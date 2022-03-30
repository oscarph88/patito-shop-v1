package com.oscar.patito.model;

import lombok.*;
import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table
public class Position {
    @Id
    @SequenceGenerator(name = "position_sequence", sequenceName = "position_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "position_sequence")
    private Integer id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;

}