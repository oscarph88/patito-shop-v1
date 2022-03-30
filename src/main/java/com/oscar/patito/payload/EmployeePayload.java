package com.oscar.patito.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeePayload {
    private Integer id;
    private String corporateEmail;
    private String firstName;
    private String lastName;
    private String gender;
    private Boolean active;
    private ContactPayload contact;
    private PositionInfoPayload position;
}
