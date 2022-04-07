package com.oscar.patito.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PositionInfoHistoryDTO {

    private Integer id;
    private String corporateEmail;
    private PositionInfoDTO positionInfo;
    private LocalDateTime last_modified;
    private Boolean current;
}
