package com.oscar.patito.model;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table
@NamedQuery(name="Employee.findActiveEmployees", query="SELECT e FROM Employee e where e.active=:active")
@NamedQuery(name="Employee.findActiveEmployeesCountryState",
            query="SELECT e FROM Employee e where e.active=:active and e.contact.country=:country and e.contact.state=:state")
@NamedQuery(name="Employee.findActiveEmployeesCountry",
        query="SELECT e FROM Employee e where e.active=:active and e.contact.country=:country")
@NamedQuery(name="Employee.findActiveEmployeesState",
        query="SELECT e FROM Employee e where e.active=:active and e.contact.state=:state")
public class Employee {
    @Id
    @SequenceGenerator(name = "employee_sequence", sequenceName = "employee_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_sequence")
    private Integer id;
    @Column(nullable = false, unique = true)
    private String corporateEmail;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private String gender;
    @Column(nullable = false)
    private Boolean active;
    @OneToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Contact contact;
    @OneToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private PositionInfo positionInfo;

}
