package com.oscar.patito.repository;

import com.oscar.patito.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    @Query(name = "Employee.findActiveEmployees")
    List<Employee> findAllActive(@Param("active") Boolean s);
}
