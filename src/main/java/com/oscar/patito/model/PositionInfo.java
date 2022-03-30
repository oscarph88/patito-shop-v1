package com.oscar.patito.model;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table
public class PositionInfo {
    @Id
    @Column(nullable = false,length = 60)
    private String corporateEmail;
    @Column
    private Integer oldPosition;
    @Column
    private Integer currentPosition;
    @Column
    private Double oldSalary;
    @Column
    private Double currentSalary;
}