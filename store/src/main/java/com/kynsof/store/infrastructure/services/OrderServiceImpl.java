package com.kynsof.store.infrastructure.services;

import com.kynsof.share.core.domain.exception.BusinessException;
import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.exception.GlobalBusinessException;
import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.ErrorField;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsof.store.application.query.order.getAll.OrderResponse;
import com.kynsof.store.domain.dto.OrderEntityDto;
import com.kynsof.store.domain.services.IOrderService;
import com.kynsof.store.infrastructure.entity.Category;
import com.kynsof.store.infrastructure.entity.Order;
import com.kynsof.store.infrastructure.repositories.command.OrderWriteDataJPARepository;
import com.kynsof.store.infrastructure.repositories.queries.OrderReadDataJPARepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Service
public class OrderServiceImpl implements IOrderService {
    private final OrderWriteDataJPARepository repositoryCommand;
    private final OrderReadDataJPARepository repositoryQuery;

    public OrderServiceImpl(OrderWriteDataJPARepository repositoryCommand, OrderReadDataJPARepository repositoryQuery) {
        this.repositoryCommand = repositoryCommand;
        this.repositoryQuery = repositoryQuery;
    }

    @Override
    public UUID create(OrderEntityDto orderDto) {
        Order entity = new Order(orderDto);
        Order additionalInformation = this.repositoryCommand.save(entity);
        return additionalInformation.getId();
    }

    @Override
    public UUID update(OrderEntityDto orderDto) {
        if (orderDto == null || orderDto.getId() == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }

        return repositoryQuery.findById(orderDto.getId())
                .map(order -> {
                    if (orderDto.getOrderDate() != null)
                        order.setOrderDate(orderDto.getOrderDate());
                    if (orderDto.getStatus() != null)
                        order.setStatus(orderDto.getStatus());
                    order.setUpdatedAt(LocalDateTime.now());
                    return repositoryCommand.save(order);
                })
                .orElseThrow(() -> new EntityNotFoundException("Order with ID " + orderDto.getId() + " not found"))
                .getId();
    }

    @Override
    public void delete(UUID id) {
        try {
            this.repositoryCommand.deleteById(id);
        } catch (Exception e) {
            throw new BusinessNotFoundException(new GlobalBusinessException(DomainErrorMessage.NOT_DELETE, new ErrorField("id", "Element cannot be deleted has a related element.")));
        }
    }

    @Override
    public OrderEntityDto findById(UUID id) {

        Optional<Order> order = this.repositoryQuery.findById(id);
        if (order.isPresent()) {
            return order.get().toAggregate();
        }
        throw new BusinessException(DomainErrorMessage.BUSINESS_NOT_FOUND, "Order not found.");
    }

    @Override
    public PaginatedResponse findAll(Pageable pageable) {
        Page<Order> data = this.repositoryQuery.findAll(pageable);
        return getPaginatedResponse(data);
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {

        GenericSpecificationsBuilder<Category> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<Order> data = this.repositoryQuery.findAll(specifications, pageable);

        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<Order> data) {
        List<OrderResponse> patients = new ArrayList<>();
        for (Order p : data.getContent()) {
            patients.add(new OrderResponse(p.toAggregate()));
        }
        return new PaginatedResponse(patients, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }
}
