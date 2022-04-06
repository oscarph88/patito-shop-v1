package com.oscar.patito.api;

import com.oscar.patito.dto.EmployeeDTO;
import com.oscar.patito.service.EmployeeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("api/patito/admin")
public class AdminApi {

    private static final Logger logger = LogManager.getLogger(AdminApi.class);

    @Autowired
    EmployeeService employeeService;


    @PostMapping("employees/create")
    @ResponseBody
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeRequest){
        try {
            EmployeeDTO employee = employeeService.saveEmployee(employeeRequest);
            logger.error("Save Success with ID "+ employee.getId() + " and mail " + employee.getCorporateEmail());
            return employee;
        }catch(DataIntegrityViolationException d){
            logger.error("Employee not created correctly");
            logger.error(d.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Employee not created correctly", d);
        }
    }

    @GetMapping("employees")
    @ResponseBody
    public List<EmployeeDTO> listEmployees(){
        List<EmployeeDTO> employees = employeeService.listEmployees();
        logger.info("Employees found " + employees.size());
        if(employees.size()>0) {
            return employees;
        }else{
            logger.info("No employees found");
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No employees found");
        }
    }

    @GetMapping("employees/{id}")
    @ResponseBody
    public EmployeeDTO getEmployee(@PathVariable Integer id){
        EmployeeDTO employee = employeeService.getEmployee(id);
            if(employee!=null) {
                logger.info("Employee found");
                return employee;
            }else {
                logger.info("No results found for id "+id);
                throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No results found for id"+id);
            }

    }

    @PutMapping("employees/update")
    @ResponseBody
    public EmployeeDTO updateEmployee(@RequestBody EmployeeDTO employeeRequest){
        try {
            EmployeeDTO employee= employeeService.updateEmployee(employeeRequest);
            logger.info("Update Success with id " + employee.getId() + " and email " + employee.getCorporateEmail());
            return employee;
        }catch(DataIntegrityViolationException d){
            logger.error("Employee not updated correctly");
            logger.error(d.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Employee not updated correctly", d);
        }

    }

    @DeleteMapping("employees/{id}/delete")
    @ResponseBody
    public String deleteEmployee(@PathVariable Integer id){
        try {
            employeeService.deleteEmployee(id);
            logger.info("Employee deleted with id "+id);
            return "Employee deleted with id "+id;
        }catch(EmptyResultDataAccessException d){
            logger.error("Employee not deleted correctly for id "+id );
            logger.error(d.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Employee not deleted correctly for id "+id, d);
        }

    }

    @PutMapping("employees/{id}/assign")
    @ResponseBody
    public EmployeeDTO assignEmployee(@RequestBody EmployeeDTO employeeRequest, @PathVariable Integer id){
        try {
            employeeRequest.setId(id);
            EmployeeDTO employee= employeeService.assignEmployee(employeeRequest);
            logger.info("Update Success with id " + employee.getId() + " and email " + employee.getCorporateEmail());
            return employee;
        }catch(DataIntegrityViolationException d){
            logger.error("Employee not updated correctly");
            logger.error(d.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Employee not updated correctly", d);
        }

    }

    @PatchMapping("employees/{id}/remove")
    @ResponseBody
    public EmployeeDTO softDeleteEmployee(@PathVariable Integer id){
        EmployeeDTO employeeDeleted = employeeService.softDeleteEmployee(id);
        if(employeeDeleted!=null) {
            logger.info("Employee deleted with id "+id);
            return employeeDeleted;
        }else{
            logger.error("Employee not deleted correctly for id "+id );
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Employee not deleted correctly for id "+id);
        }

    }

    @GetMapping("employee/filter")
    @ResponseBody
    public List<EmployeeDTO> filterEmployees(@RequestParam(required = false) String firstName,
                                             @RequestParam(required = false) String lastName,
                                             @RequestParam(required = false) String position,
                                             @RequestParam(required = false) boolean deleted){
        logger.info("Searching for " + firstName +" , "+lastName +" , "+ position +" , "+ deleted);
        List<EmployeeDTO> employees = employeeService.filterEmployees(firstName, lastName, position, deleted);
        logger.info("Employees found " + employees.size());
        if(employees.size()>0) {
            return employees;
        }else{
            logger.info("No employees found");
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No employees found");
        }
    }
}
