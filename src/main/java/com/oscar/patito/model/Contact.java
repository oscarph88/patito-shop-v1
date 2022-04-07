package com.oscar.patito.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

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
    private String phoneNumber;
    @Column(nullable = false)
    private String address;
    @Column
    private String city;
    @Column
    private String state;
    @Column
    private String country;
    @Column
    private LocalDateTime birthday;
}