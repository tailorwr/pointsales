package com.kynsof.identity.domain.dto;

import com.kynsof.identity.domain.dto.enumType.PermissionStatusEnm;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PermissionDto {
    private UUID id;
    private String code;
    private String description;
    private PermissionStatusEnm status;

    /**
     * Usar este constructor en el create.
     * @param id
     * @param code
     * @param description 
     */
    public PermissionDto(UUID id, String code, String description) {
        this.id = id;
        this.code = code;
        this.description = description;
    }

}
