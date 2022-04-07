package com.oscar.patito.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
    private Integer id;
    @NotNull(message = "email cannot be null")
    @Email(message = "email incorrect")
    private String corporateEmail;
    @NotNull(message = "Name cannot be null")
    private String firstName;
    private String lastName;
    private String gender;
    private Boolean active;
    private ContactDTO contact;
    private PositionInfoDTO positionInfo;
}
