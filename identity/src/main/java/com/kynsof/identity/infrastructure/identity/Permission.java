package com.kynsof.identity.infrastructure.identity;

import com.kynsof.identity.domain.dto.PermissionStatusEnm;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Permission {
    @Id
    @Column(name = "id")
    private UUID id;
    @Column(unique = true)
    private String code;
    private String description;
    @Enumerated(EnumType.STRING)
    private PermissionStatusEnm status;

    @OneToMany(mappedBy = "permission")
    private List<RolePermission> rolPermissions = new ArrayList<>();
}
