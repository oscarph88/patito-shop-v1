package com.oscar.patito.dto;

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
    private Long phoneNumber;
    private String address;
    private String city;
    private String state;
    private String country;
    private LocalDateTime birthday;
}
