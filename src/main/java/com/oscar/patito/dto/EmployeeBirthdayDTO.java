package com.oscar.patito.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeBirthdayDTO {

    private List<EmployeeNoAdminDTO> currentWeek;
    private List<EmployeeNoAdminDTO> nextWeek;
}
