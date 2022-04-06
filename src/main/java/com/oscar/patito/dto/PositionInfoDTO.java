package com.oscar.patito.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PositionInfoDTO {

    private String corporateEmail;
    private PositionDTO oldPosition;
    private PositionDTO currentPosition;
    private Double oldSalary;
    private Double currentSalary;
    private Boolean active;
}
