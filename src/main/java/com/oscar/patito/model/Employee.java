package com.oscar.patito.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Employee {
    @Id
    private String corporateEmail;
    private String firstName;
    private String lastName;
    private String gender;
    private Boolean active;
}
