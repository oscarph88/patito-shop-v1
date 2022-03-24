package com.oscar.patito.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Contact {
    @Id
    private String corporateEmail;
    private String personalEmail;
    private Long phoneNumber;
    private String address;
    private String city;
    private String state;
    private String country;
    private Date birthday;
}