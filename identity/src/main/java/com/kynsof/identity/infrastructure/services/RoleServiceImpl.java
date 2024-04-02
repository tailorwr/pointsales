package com.kynsof.identity.infrastructure.services;

import com.kynsof.identity.application.query.roles.getSearch.RoleSystemsResponse;
import com.kynsof.identity.domain.dto.RoleDto;
import com.kynsof.identity.domain.dto.RolePermissionDto;
import com.kynsof.identity.domain.dto.enumType.RoleStatusEnm;
import com.kynsof.identity.domain.dto.roleDto.ModuleWithPermissionsDto;
import com.kynsof.identity.domain.dto.roleDto.PermissionRoleDto;
import com.kynsof.identity.domain.dto.roleDto.RoleWithModulesResponse;
import com.kynsof.identity.domain.interfaces.service.IRoleService;
import com.kynsof.identity.domain.rules.RolNameMustBeUniqueRule;
import com.kynsof.identity.infrastructure.identity.ModuleSystem;
import com.kynsof.identity.infrastructure.identity.Permission;
import com.kynsof.identity.infrastructure.identity.RolePermission;
import com.kynsof.identity.infrastructure.identity.RoleSystem;
import com.kynsof.identity.infrastructure.repository.command.RolWriteDataJPARepository;
import com.kynsof.identity.infrastructure.repository.query.ModuleReadDataJPARepository;
import com.kynsof.identity.infrastructure.repository.query.PermissionReadDataJPARepository;
import com.kynsof.identity.infrastructure.repository.query.RolReadDataJPARepository;
import com.kynsof.share.core.domain.RulesChecker;
import com.kynsof.share.core.domain.exception.BusinessException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.domain.rules.ValidateObjectNotNullRule;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private RolWriteDataJPARepository repositoryCommand;

    @Autowired
    private RolReadDataJPARepository repositoryQuery;

    @Autowired
    private ModuleReadDataJPARepository moduleReadDataJPARepository;

    @Autowired
    private PermissionReadDataJPARepository permissionReadDataJPARepository;

    @Override
    public UUID create(RoleDto dto) {
        RulesChecker.checkRule(new RolNameMustBeUniqueRule(this, dto));
        RoleSystem rol = this.repositoryCommand.save(new RoleSystem(dto));
        return rol.getId();
    }

    @Override
    public void update(RoleDto roleUpdateDto) {
        RulesChecker.checkRule(new ValidateObjectNotNullRule(roleUpdateDto, "Role", "Role DTO cannot be null."));
        RulesChecker.checkRule(new ValidateObjectNotNullRule(roleUpdateDto.getId(), "Role.id", "Role ID cannot be null."));

        RoleSystem object = this.repositoryQuery.findById(roleUpdateDto.getId())
                .orElseThrow(() -> new BusinessException(DomainErrorMessage.ROLE_NOT_FOUND, "Role not found."));

        object.setDescription(roleUpdateDto.getDescription() != null ? roleUpdateDto.getDescription() : object.getDescription());
        object.setName(roleUpdateDto.getName() != null ? roleUpdateDto.getName() : object.getName());
        object.setStatus(roleUpdateDto.getStatus() != object.getStatus() ? roleUpdateDto.getStatus() : object.getStatus());

        this.repositoryCommand.save(object);

    }

    @Override
    public void delete(UUID id) {
        RoleDto delete = this.findById(id);
        delete.setDeleted(true);
        delete.setStatus(RoleStatusEnm.INACTIVE);
        this.repositoryCommand.save(new RoleSystem(delete));
    }

    @Override
    public RoleDto findById(UUID id) {
        Optional<RoleSystem> rolSystem = this.repositoryQuery.findById(id);
        if (rolSystem.isPresent()) {
            return rolSystem.get().toAggregate();
        }
        throw new BusinessException(DomainErrorMessage.ROLE_NOT_FOUND, "Role not found.");
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        filterCreteria(filterCriteria);

        GenericSpecificationsBuilder<RoleSystem> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<RoleSystem> data = this.repositoryQuery.findAll(specifications, pageable);

        return getPaginatedResponse(data);
    }

    private void filterCreteria(List<FilterCriteria> filterCriteria) {
        for (FilterCriteria filter : filterCriteria) {
            if ("status".equals(filter.getKey()) && filter.getValue() instanceof String) {
                try {
                    RoleStatusEnm enumValue = RoleStatusEnm.valueOf((String) filter.getValue());
                    filter.setValue(enumValue);
                } catch (IllegalArgumentException e) {
                    System.err.println("Valor inválido para el tipo Enum Empresa: " + filter.getValue());
                }
            }
        }
    }

    private PaginatedResponse getPaginatedResponse(Page<RoleSystem> data) {
        List<RoleSystemsResponse> allergyResponses = new ArrayList<>();
        for (RoleSystem p : data.getContent()) {
            allergyResponses.add(new RoleSystemsResponse(p.toAggregate()));
        }
        return new PaginatedResponse(allergyResponses, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

    @Override
    public List<RolePermissionDto> findPermissionForRoleById(UUID id) {
        Optional<RoleSystem> rolSystem = this.repositoryQuery.findById(id);
        if (rolSystem.isPresent()) {
            List<RolePermissionDto> permissions = new ArrayList<>();
            for (RolePermission rolePermission : rolSystem.get().getRolePermissions()) {
                if (!rolePermission.isDeleted()) {
                    permissions.add(rolePermission.toAggregate());
                }
            }
            return permissions;
        }
        throw new BusinessException(DomainErrorMessage.ROLE_NOT_FOUND, "Role not found.");
    }

    @Override
    public Long countByName(String name) {
        return this.repositoryQuery.countByName(name);
    }

    @Override
    public RoleWithModulesResponse getRoleWithModulesAndPermissions(UUID roleId) {
        RoleSystem role = repositoryQuery.findById(roleId)
                .orElseThrow(() -> new EntityNotFoundException("Role not found"));

        List<ModuleWithPermissionsDto> modulesWithPermissions = new ArrayList<>();

        List<ModuleSystem> modules = moduleReadDataJPARepository.findModulesByRoleId(roleId);

        for (ModuleSystem module : modules) {
            Set<Permission> permissions = permissionReadDataJPARepository.findPermissionsByModuleIdAndRoleId(module.getId(), roleId);

            List<PermissionRoleDto> permissionDtos = permissions.stream()
                    .map(permission -> new PermissionRoleDto(permission.getId(), permission.getCode(), permission.getDescription(), permission.getAction()))
                    .collect(Collectors.toList());

            ModuleWithPermissionsDto moduleDto = new ModuleWithPermissionsDto(module.getId(), module.getName(), module.getDescription(), permissionDtos);
            modulesWithPermissions.add(moduleDto);
        }

        return new RoleWithModulesResponse(role.getId(), role.getName(), role.getDescription(), modulesWithPermissions);
    }
}
