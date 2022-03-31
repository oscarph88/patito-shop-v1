package com.oscar.patito.api;

import com.oscar.patito.model.Employee;
import com.oscar.patito.payload.EmployeePayload;
import com.oscar.patito.payload.PositionInfoPayload;
import com.oscar.patito.service.EmployeeService;
import com.oscar.patito.service.PositionService;
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
public class PatitoRestApi {

    private static final Logger logger = LogManager.getLogger(PatitoRestApi.class);

    @Autowired
    EmployeeService employeeService;
    @Autowired
    PositionService positionService;

    @GetMapping("printTest")
    public String printTest() {
        return "Hello!";
    }

    @PostMapping("saveEmployee")
    public String saveEmployee(@RequestBody EmployeePayload employeeRequest){
        try {
            Employee employee = employeeService.saveEmployee(employeeRequest);
            return"Save Success with " + employee.getCorporateEmail();
        }catch(DataIntegrityViolationException d){
            logger.error("Employee not created correctly");
            logger.error(d.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Employee not created correctly", d);
        }
    }

    @GetMapping("listEmployees")
    public List<EmployeePayload> listEmployees(){
        List<EmployeePayload> employees = employeeService.listEmployees();
        logger.info("Employees found " + employees.size());
        if(employees.size()>0) {
            return employees;
        }else{
            logger.info("No employees found");
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No employees found");
        }
    }

    @GetMapping("listEmployee")
    public EmployeePayload listEmployee(@RequestParam("id") Integer id){
        EmployeePayload employee = employeeService.listEmployee(id);
            if(employee!=null) {
                logger.info("Employee found");
                return employee;
            }else {
                logger.info("No results found for id "+id);
                throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No results found for id"+id);
            }

    }

    @PutMapping("updateEmployee")
    public String updateEmployee(@RequestBody EmployeePayload employeeRequest){
        try {
            Employee employee= employeeService.updateEmployee(employeeRequest);
            logger.info("Update Success with id " + employee.getId() + " and email " + employee.getCorporateEmail());
            return "Update Success with id " + employee.getId() + " and email " + employee.getCorporateEmail();
        }catch(DataIntegrityViolationException d){
            logger.error("Employee not updated correctly");
            logger.error(d.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Employee not updated correctly", d);
        }

    }

    @DeleteMapping("deleteEmployee")
    public String deleteEmployee(@RequestParam("id") Integer id){
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

    @PatchMapping("softDeleteEmployee")
    public String softDeleteEmployee(@RequestParam("id") Integer id){
        Employee employeeDeleted = employeeService.softDeleteEmployee(id);
        if(employeeDeleted!=null) {
            logger.info("Employee deleted with id "+id);
            return "Employee deleted with id "+id;
        }else{
            logger.error("Employee not deleted correctly for id "+id );
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Employee not deleted correctly for id "+id);
        }

    }

    @GetMapping("listPositionsInfo")
    public List<PositionInfoPayload> listPositionsInfo() {
        List<PositionInfoPayload> positionsInfo = positionService.listPositionsInfo();
        logger.info("Positions info found " + positionsInfo.size());
        long n = positionsInfo.stream().distinct().count();
        logger.info("N " + n);
        if (positionsInfo.size() > 0) {
            return positionsInfo;
        } else {
            logger.info("No positions info found");
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No positions info found");
        }
    }

}
