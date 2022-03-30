package com.oscar.patito.payload;

import com.oscar.patito.model.Position;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PositionInfoPayload {

    private String corporateEmail;
    private Integer oldPosition;
    private String oldPositionDesc;
    private Integer currentPosition;
    private String currentPositionDesc;
    private Double oldSalary;
    private Double currentSalary;
}
