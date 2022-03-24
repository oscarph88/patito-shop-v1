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
public class Position {
    @Id
    private String corporateEmail;
    private String oldPosition;
    private String currentPosition;
    private Double oldSalary;
    private Double currentSalary;
}