package com.oscar.patito.api;

import com.oscar.patito.payload.EmployeeNoAdminPayload;
import com.oscar.patito.payload.EmployeePayload;
import com.oscar.patito.service.EmployeeNoAdminService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("api/patito/user")
public class UserApi {

    private static final Logger logger = LogManager.getLogger(UserApi.class);
    @Autowired
    EmployeeNoAdminService employeeNAService;

    @GetMapping("listEmployees")
    public List<EmployeeNoAdminPayload> listEmployees(){
        List<EmployeeNoAdminPayload> employees = employeeNAService.listEmployees();
        logger.info("Employees found " + employees.size());
        if(employees.size()>0) {
            return employees;
        }else{
            logger.info("No employees found");
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No employees found");
        }
    }

    @GetMapping("filterEmployees")
    public List<EmployeeNoAdminPayload> filterEmployees(@RequestParam(required = false) String firstName,
                                                 @RequestParam(required = false) String lastName, @RequestParam(required = false) String position){
        List<EmployeeNoAdminPayload> employees = employeeNAService.filterEmployees(firstName, lastName, position);
        logger.info("Employees found " + employees.size());
        if(employees.size()>0) {
            return employees;
        }else{
            logger.info("No employees found");
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No employees found");
        }
    }

    @GetMapping("getEmployee")
    public EmployeeNoAdminPayload getEmployee(@RequestParam("id") int id){
        EmployeeNoAdminPayload employee = employeeNAService.getEmployee(id);
        if(employee!=null) {
            logger.info("Employee found");
            return employee;
        }else {
            logger.info("No results found for id "+id);
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No results found for id"+id);
        }

    }
}
