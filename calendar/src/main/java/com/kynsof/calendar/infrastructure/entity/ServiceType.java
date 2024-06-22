package com.kynsof.calendar.infrastructure.entity;

import com.kynsof.calendar.domain.dto.ServiceTypeDto;
import com.kynsof.calendar.domain.dto.enumType.EServiceStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "service_type")
public class ServiceType {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Size(max = 150)
    @NotBlank
    private String name;
    private String picture;

    @Enumerated(EnumType.STRING)
    private EServiceStatus status;

    @OneToMany(mappedBy = "type")
    private Set<Services> services;

    @CreationTimestamp
    @Column(nullable = true, updatable = true)
    private LocalDateTime createdAt;

    @Column(nullable = true, updatable = true)
    private LocalDateTime updatedAt;

    public ServiceType(ServiceTypeDto dto) {
        this.id = dto.getId();
        this.name = dto.getName();
        this.picture = dto.getPicture();
        this.status = dto.getStatus();
    }

    public ServiceTypeDto toAggregate() {
        return new ServiceTypeDto(this.id, this.name,this.picture, status);
    }
}
