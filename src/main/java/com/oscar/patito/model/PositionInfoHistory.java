package com.oscar.patito.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table
@NamedQuery(name="History.findCurrentHistory",
        query="SELECT p FROM PositionInfoHistory p where p.current=:current and p.corporateEmail=:email")
@NamedQuery(name="History.active.findCurrentHistory",
        query="SELECT p FROM PositionInfoHistory p where p.corporateEmail=:email")
public class PositionInfoHistory {

    @Id
    @SequenceGenerator(name = "history_sequence", sequenceName = "history_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "history_sequence")
    private Integer id;
    @Column(nullable = false,length = 60)
    private String corporateEmail;
    @ManyToOne
    private PositionInfo positionInfo;
    @Column
    private LocalDateTime last_modified;
    @Column
    private Boolean current;
}
