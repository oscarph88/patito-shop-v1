package com.oscar.patito.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table
public class Position {
    @Id
    @Column(nullable = false,length = 60)
    private String corporateEmail;
    @Column
    private String oldPosition;
    @Column
    private String currentPosition;
    @Column
    private Double oldSalary;
    @Column
    private Double currentSalary;
}