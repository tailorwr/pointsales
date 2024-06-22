package com.kynsof.patients.infrastructure.entity;

import com.kynsof.patients.domain.dto.CurrentMerdicationEntityDto;
import com.kynsof.patients.domain.dto.enumTye.Status;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class CurrentMedication {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;

    private String dosage;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "medical_information_id", nullable = false)
    private MedicalInformation medicalInformation;

    @CreationTimestamp
    @Column(nullable = true, updatable = true)
    private LocalDateTime createdAt;

    @Column(nullable = true, updatable = true)
    private LocalDateTime updatedAt;

    public CurrentMedication(CurrentMerdicationEntityDto dto) {
        this.id = dto.getId();
        this.name = dto.getName();
        this.dosage = dto.getDosage();
        this.status = dto.getStatus();
        this.medicalInformation = new MedicalInformation(dto.getMedicalInformationDto());
    }

    public CurrentMerdicationEntityDto toAggregate() {
        return new CurrentMerdicationEntityDto(this.id, this.name, this.dosage, this.status,
                this.medicalInformation.getId(), this.medicalInformation.toAggregate());
    }
}
