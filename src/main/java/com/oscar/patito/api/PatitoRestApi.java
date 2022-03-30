package com.oscar.patito.api;

import com.oscar.patito.model.Employee;
import com.oscar.patito.model.Position;
import com.oscar.patito.payload.EmployeePayload;
import com.oscar.patito.payload.PositionInfoPayload;
import com.oscar.patito.payload.PositionPayload;
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
public class PatitoRestApi {

    private static final Logger logger = LogManager.getLogger(PatitoRestApi.class);

    @Autowired
    EmployeeService employeeService;

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

    @PostMapping("loadEmployees")
    public String loadEmployees(@RequestBody List<EmployeePayload> employeeRequests){
        try {
            List<Employee> employees = employeeService.saveEmployeeAll(employeeRequests);
            return "Entries created " + employees.size();
        }catch(DataIntegrityViolationException d){
            logger.error("Employees not created correctly");
            logger.error(d.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Employees not created correctly", d);
        }
    }

    @GetMapping("listEmployees")
    public List<EmployeePayload> listEmployees(){
        List<EmployeePayload> employees = employeeService.listEmployees();
        logger.info("Employees found " + employees.size());
        return employees;
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
    public String DeleteEmployee(@RequestParam("id") Integer id){
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

    @PostMapping("loadPositions")
    public String loadPositions(@RequestBody List<PositionPayload> positionsRequests){
        try {
            List<Position> positions = employeeService.savePositions(positionsRequests);
            return "Positions created " + positions.size();
        }catch(DataIntegrityViolationException d){
            logger.error("Positions not created correctly");
            logger.error(d.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Positions not created correctly", d);
        }
    }

    @GetMapping("listPositions")
    public List<PositionPayload> listPositions(){
        List<PositionPayload> positions = employeeService.listPositions();
        logger.info("Positions found " + positions.size());
        return positions;
    }

    @GetMapping("listPositionsInfo")
    public List<PositionInfoPayload> listPositionsInfo(){
        List<PositionInfoPayload> positionsInfo = employeeService.listPositionsInfo();
        logger.info("Positions info found " + positionsInfo.size());
        return positionsInfo;
    }

}
