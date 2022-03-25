package com.oscar.patito.service;

import com.oscar.patito.helper.EmployeeHelper;
import com.oscar.patito.model.Employee;
import com.oscar.patito.payload.EmployeePayload;
import com.oscar.patito.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    EmployeeHelper eh= new EmployeeHelper();

    public Employee saveEmployee(EmployeePayload emp)throws DataIntegrityViolationException {
        Employee employee= eh.generateEmployee(emp);
        System.out.println("Entry success xxx");
        return employeeRepository.save(employee);
    }

    public List<Employee> saveEmployeeAll(List<EmployeePayload> employees)throws DataIntegrityViolationException {
        List<Employee> empSaved= new ArrayList<>();
        for(EmployeePayload emp: employees) {
            Employee employee= eh.generateEmployee(emp);
            System.out.println("List Entry success xxx");
            empSaved.add(employeeRepository.save(employee));
        }
        return empSaved;
    }

    public List<EmployeePayload> listEmployees(){
        List<Employee> employees = employeeRepository.findAll();
        System.out.println("List complete");
        return employees.stream().map(e -> new EmployeePayload(e.getId(),e.getCorporateEmail(), e.getFirstName(),
                e.getLastName(), e.getGender(), e.getActive(), e.getContact(), e.getPosition())).collect(Collectors.toList());
    }

    public EmployeePayload listEmployee(Integer id){
        Optional<Employee> employee = employeeRepository.findById(id);
        //return employee.isPresent()?eh.generateEmployeePayload(employee.get()):null;
        return employee.map(value -> eh.generateEmployeePayload(value)).orElse(null);
    }

    public Employee updateEmployee(EmployeePayload emp){
        Employee employee= eh.generateEmployee(emp);
        return employeeRepository.saveAndFlush(employee);
    }

    public void deleteEmployee(Integer id){
        employeeRepository.deleteById(id);
    }
}
