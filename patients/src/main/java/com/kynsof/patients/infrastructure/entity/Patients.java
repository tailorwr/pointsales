package com.kynsof.patients.infrastructure.entity;

import com.kynsof.patients.domain.dto.Status;
import com.kynsof.patients.domain.dto.PatientDto;
import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Patients {
    @Id
    @Column(name="id")
    private UUID id;

    @Column(unique = true)
    private String identification;

    private String name;

    private String lastName;

    private String gender;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy = "patient", orphanRemoval = true)
    private List<ContactInformation> contactInformation;

    @OneToOne(mappedBy = "patient",  orphanRemoval = true, fetch = FetchType.LAZY)
    private MedicalInformation medicalInformation;

    @OneToOne(mappedBy = "patient",  orphanRemoval = true, fetch = FetchType.LAZY)
    private AdditionalInformation additionalInformation;

    @ManyToMany
    @JoinTable(name = "patient_insurance",
            joinColumns = @JoinColumn(name = "patient_id"),
            inverseJoinColumns = @JoinColumn(name = "insurance_id")
    )
    private List<Insurance> insurances;

    public Patients(PatientDto patients) {
        this.id = patients.getId();
        this.identification = patients.getIdentification();
        this.name = patients.getName();
        this.lastName = patients.getLastName();
        this.gender = patients.getGender();
        this.status = patients.getStatus();
    }

    public PatientDto toAggregate() {
        return new PatientDto(id, identification, name, lastName, gender, status);
    }
}
