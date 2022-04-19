package com.oscar.patito.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.OptBoolean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactDTO {

    private String corporateEmail;
    private String personalEmail;
    private String phoneNumber;
    private String address;
    private String city;
    private String state;
    private String country;
    //@JsonFormat(lenient = OptBoolean.FALSE, shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
    private LocalDateTime birthday;
}
