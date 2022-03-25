package com.oscar.patito.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table
public class Contact {
    @Id
    @Column(nullable = false,length = 60)
    private String corporateEmail;
    @Column
    private String personalEmail;
    @Column
    private Long phoneNumber;
    @Column(nullable = false)
    private String address;
    @Column
    private String city;
    @Column
    private String state;
    @Column
    private String country;
    @Column
    private Date birthday;
}