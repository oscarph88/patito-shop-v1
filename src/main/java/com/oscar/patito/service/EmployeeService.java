package com.oscar.patito.service;

import com.oscar.patito.model.Employee;
import com.oscar.patito.payload.EmployeePayload;
import com.oscar.patito.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee saveEmployee(EmployeePayload emp){
        Employee employee = new Employee();
        employee.setCorporateEmail(emp.getCorporateEmail());
        employee.setFirstName(emp.getFirstName());
        employee.setLastName(emp.getLastName());
        employee.setGender(emp.getGender());
        employee.setActive(emp.getActive());
        System.out.println("Entry success");
        return employeeRepository.save(employee);
    }

    public List<EmployeePayload> listEmployees(){
        List<Employee> employees = employeeRepository.findAll();
        System.out.println("List complete");
        return employees.stream().map(e -> new EmployeePayload(e.getCorporateEmail(), e.getFirstName(),
                e.getLastName(), e.getGender(), e.getActive())).collect(Collectors.toList());
    }

    public Employee updateEmployee(EmployeePayload emp){
        Employee employee = new Employee();
        employee.setCorporateEmail(emp.getCorporateEmail());
        employee.setFirstName(emp.getFirstName());
        employee.setLastName(emp.getLastName());
        employee.setGender(emp.getGender());
        employee.setActive(emp.getActive());
        return employeeRepository.saveAndFlush(employee);
    }

    public void deleteEmployee(String email){
        employeeRepository.deleteById(email);
    }
}
