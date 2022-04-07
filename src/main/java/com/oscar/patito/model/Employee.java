package com.oscar.patito.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table
@NamedQuery(name="Employee.findActiveEmployees",
        query="SELECT e FROM Employee e where e.active=:active")
@NamedQuery(name="Employee.findActiveEmployeesCountryState",
            query="SELECT e FROM Employee e where e.active=:active and e.contact.country=:country and e.contact.state=:state")
@NamedQuery(name="Employee.findActiveEmployeesCountry",
        query="SELECT e FROM Employee e where e.active=:active and e.contact.country=:country")
@NamedQuery(name="Employee.findActiveEmployeesState",
        query="SELECT e FROM Employee e where e.active=:active and e.contact.state=:state")
@NamedQuery(name="Employee.normal.findActiveEmployee",
        query="SELECT e FROM Employee e where e.active=:active and e.id=:id")
@NamedQuery(name="Employee.findTodayEmployeeBirthday",
        query="SELECT e FROM Employee e where e.active=:active and e.contact.birthday>=:today and e.contact.birthday<:tomorrow")
@NamedQuery(name="Employee.findTodayEmployeeBirthday2",
        query="SELECT e FROM Employee e where e.active=:active and day(e.contact.birthday) =day(:today) and month(e.contact.birthday) =month(:today)")
@NamedQuery(name="Employee.findNextWeekEmployeeBirthday",
        query="SELECT e FROM Employee e where e.active=:active and e.contact.birthday>=:startDate and e.contact.birthday<:endDate")
@NamedQuery(name="Employee.findNextWeekEmployeeBirthday2",
        query="SELECT e FROM Employee e where e.active=:active " +
                "and month(e.contact.birthday) >=month(:startDate) and  month(e.contact.birthday) <=month(:endDate) " +
                "and day(e.contact.birthday) >= day(:startDate) and day(e.contact.birthday) <=day(:endDate)")
@NamedQuery(name="Employee.findActiveEmployees.email",
        query="SELECT e.corporateEmail FROM Employee e where e.active=:active")
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
