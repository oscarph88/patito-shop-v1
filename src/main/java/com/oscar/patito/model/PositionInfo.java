package com.oscar.patito.model;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table
@NamedQuery(name="PositionInfo.findActivePositions", query="SELECT pi FROM PositionInfo pi where pi.active=:active")
public class PositionInfo {
    @Id
    @Column(nullable = false,length = 60)
    private String corporateEmail;
    @ManyToOne(optional = true)
    private Position oldPosition;
    @ManyToOne(optional = true)
    private Position currentPosition;
    @Column
    private Double oldSalary;
    @Column
    private Double currentSalary;
    @Column
    private Boolean active;
}