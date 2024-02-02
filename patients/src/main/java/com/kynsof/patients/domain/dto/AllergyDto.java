package com.kynsof.patients.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AllergyDto {
    private UUID id;
    private String code;
    private String name;
}
