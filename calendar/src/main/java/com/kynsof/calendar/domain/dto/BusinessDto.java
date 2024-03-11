package com.kynsof.calendar.domain.dto;

import com.kynsof.calendar.domain.dto.enumType.EBusinessStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BusinessDto implements Serializable {
    private UUID id;
    private String name;
    private String latitude;
    private String longitude;
    private String description;
    private byte[] logo;
    private UUID idLogo;
    private String ruc;
    private EBusinessStatus status;
    private boolean deleted;

    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private LocalDateTime deleteAt;

    public BusinessDto(UUID id, String name, String latitude, String longitude, String description, byte[] logo, String ruc, EBusinessStatus status) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
        this.logo = logo;
        this.ruc = ruc;
        this.status = status;
    }

    public BusinessDto(UUID id, String name, String latitude, String longitude, String description, byte[] logo, UUID idLogo, String ruc) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
        this.logo = logo;
        this.idLogo = idLogo;
        this.ruc = ruc;
    }

}
