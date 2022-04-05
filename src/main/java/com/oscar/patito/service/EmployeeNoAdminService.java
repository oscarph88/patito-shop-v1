package com.oscar.patito.service;

import com.oscar.patito.helper.EmployeeHelper;
import com.oscar.patito.model.Employee;
import com.oscar.patito.payload.EmployeeNoAdminPayload;
import com.oscar.patito.payload.EmployeePayload;
import com.oscar.patito.repository.EmployeeRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeNoAdminService {

    @Autowired
    private EmployeeRepository employeeRepository;

    private static final Logger logger = LogManager.getLogger(EmployeeNoAdminService.class);
    private EmployeeHelper eh= new EmployeeHelper();

    public List<EmployeeNoAdminPayload> listEmployees(){
        //List<Employee> employees = employeeRepository.findAll();
        List<Employee> employees = employeeRepository.findAllActive(true);
        logger.info("Employee list completed");
        return employees.stream().map(e -> new EmployeeNoAdminPayload(e.getId(),e.getCorporateEmail(), e.getFirstName(),
                        e.getLastName(), e.getGender(), eh.generateContactPayload(e.getContact()),
                        e.getPositionInfo().getCurrentPosition().getName()))
                .collect(Collectors.toList());
    }

    public List<EmployeeNoAdminPayload> filterEmployees(String firstname, String lastname, String position){
        List<Employee> employees = employeeRepository.findAll();
        logger.info("Employee list completed");
        return employees.stream()
                .filter(t -> t.getFirstName().toLowerCase().contains(firstname!=null?firstname.toLowerCase():"") &&
                        t.getLastName().toLowerCase().contains(lastname!=null?lastname.toLowerCase():"") &&
                        t.getPositionInfo().getCurrentPosition().getName().toLowerCase().contains(position!=null?position.toLowerCase():""))
                .collect(Collectors.toList()).stream().map(e -> new EmployeeNoAdminPayload(e.getId(),e.getCorporateEmail(), e.getFirstName(),
                        e.getLastName(), e.getGender(), eh.generateContactPayload(e.getContact()),
                        e.getPositionInfo().getCurrentPosition().getName()))
                .collect(Collectors.toList());
    }

    public EmployeeNoAdminPayload getEmployee(int id){
        logger.info("Searching for employee "+id);
        Employee employee = employeeRepository.findActiveEmployee(true, id);
        //return employee.map(value -> eh.generateEmployeePayload(value)).orElse(null);
        return new EmployeeNoAdminPayload(employee.getId(),employee.getCorporateEmail(), employee.getFirstName(),
                employee.getLastName(), employee.getGender(), eh.generateContactPayload(employee.getContact()),
                employee.getPositionInfo().getCurrentPosition().getName());
    }
}
