package com.kynsof.calendar.infrastructure.entity;

import com.kynsof.calendar.domain.dto.ServiceDto;
import com.kynsof.calendar.domain.dto.enumType.EServiceStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Services {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @ManyToOne()
    @JoinColumn(name = "service_type_id") // Asume una columna foreign key service_type_id
    private ServiceType type;

    @Enumerated(EnumType.STRING)
    private EServiceStatus status;

    private String picture;
    private String name;
    private Double normalAppointmentPrice;
    private Double expressAppointmentPrice;
    @Column(nullable = true)
    private Integer estimatedDuration;

    @Size(max = 2000)
    private String description;

    @OneToMany(mappedBy = "service",fetch = FetchType.EAGER)
    private Set<ResourceService> resourceServices = new HashSet<>();

    @OneToMany(mappedBy = "service")
    private Set<Schedule> schedules = new HashSet<>();

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean applyIva = true;

    @CreationTimestamp
    @Column(nullable = true, updatable = true)
    private LocalDateTime createdAt;

    @Column(nullable = true, updatable = true)
    private LocalDateTime updatedAt;

    public Services(ServiceDto object) {
        this.id = object.getId();
        this.type = new ServiceType(object.getType());
        this.status = object.getStatus();
        this.picture = object.getPicture();
        this.name = object.getName();
        this.normalAppointmentPrice = object.getNormalAppointmentPrice();
        this.expressAppointmentPrice = object.getExpressAppointmentPrice();
        this.description = object.getDescription();
        this.applyIva = object.getApplyIva();
        this.estimatedDuration = object.getEstimatedDuration();
    }

    public ServiceDto toAggregate () {
        return new ServiceDto(id, type.toAggregate(), status, picture, name, normalAppointmentPrice,
                expressAppointmentPrice, description, applyIva, estimatedDuration);
    }
}
