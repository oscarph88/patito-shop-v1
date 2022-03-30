package com.oscar.patito.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PositionPayload {

    private Integer id;
    private String name;
    private String description;
}
