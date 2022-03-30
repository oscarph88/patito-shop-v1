package com.oscar.patito.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactPayload {

    private String corporateEmail;
    private String personalEmail;
    private Long phoneNumber;
    private String address;
    private String city;
    private String state;
    private String country;
    private Date birthday;
}
