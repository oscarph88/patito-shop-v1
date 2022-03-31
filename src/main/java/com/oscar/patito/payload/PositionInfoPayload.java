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
    private PositionPayload oldPosition;
    private PositionPayload currentPosition;
    private Double oldSalary;
    private Double currentSalary;
    private Boolean active;
}
