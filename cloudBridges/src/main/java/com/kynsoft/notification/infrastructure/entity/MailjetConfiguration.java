package com.kynsoft.notification.infrastructure.entity;


import com.kynsoft.notification.domain.dto.MailjetConfigurationDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MailjetConfiguration implements Serializable {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String mailjetApiKey;

    @Column(nullable = false)
    private String mailjetApiSecret;

    @Column(nullable = false)
    private String fromEmail;

    @Column(nullable = false)
    private String fromName;

    @ManyToOne()
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "mailjetConfig", cascade = CascadeType.ALL)
    private List<TemplateEntity> templates;

    public MailjetConfigurationDto toAggregate() {
        return new MailjetConfigurationDto(
                id, 
                mailjetApiKey, 
                mailjetApiSecret, 
                fromEmail, 
                fromName, 
                tenant != null ? tenant.toAggregate() : null, 
                createdAt
        );
    }

    public MailjetConfiguration(MailjetConfigurationDto dto) {
        this.mailjetApiKey = dto.getMailjetApiKey();
        this.mailjetApiSecret = dto.getMailjetApiSecret();
        this.fromEmail = dto.getFromEmail();
        this.fromName = dto.getFromName();
        this.id = dto.getId();
        this.tenant = dto.getTenant() != null ? new Tenant(dto.getTenant()) : null;
    }

}