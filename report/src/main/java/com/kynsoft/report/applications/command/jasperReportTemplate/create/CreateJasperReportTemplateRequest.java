package com.kynsoft.report.applications.command.jasperReportTemplate.create;

import com.kynsoft.report.domain.dto.JasperReportTemplateType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateJasperReportTemplateRequest {
    private String code;
    private String name;
    private String description;
    private JasperReportTemplateType type;
    private byte[] file;
    private String parameters;
}
