package com.oscar.patito.api;

import com.oscar.patito.dto.EmployeeBirthdayDTO;
import com.oscar.patito.dto.EmployeeNoAdminDTO;
import com.oscar.patito.service.EmployeeNoAdminService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("api/patito/user")
public class UserApi {

    private static final Logger logger = LogManager.getLogger(UserApi.class);
    @Autowired
    EmployeeNoAdminService employeeNAService;

    @GetMapping("employee")
    @ResponseBody
    public List<EmployeeNoAdminDTO> listEmployees(){
        List<EmployeeNoAdminDTO> employees = employeeNAService.listEmployees();
        logger.info("Employees found " + employees.size());
        if(employees.size()>0) {
            return employees;
        }else{
            logger.info("No employees found");
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No employees found");
        }
    }

    @GetMapping("employee/filter")
    @ResponseBody
    public List<EmployeeNoAdminDTO> filterEmployees(@RequestParam(required = false) String firstName,
                                                    @RequestParam(required = false) String lastName, @RequestParam(required = false) String position){
        List<EmployeeNoAdminDTO> employees = employeeNAService.filterEmployees(firstName, lastName, position);
        logger.info("Employees found " + employees.size());
        if(employees.size()>0) {
            return employees;
        }else{
            logger.info("No employees found");
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No employees found");
        }
    }

    @GetMapping("employee/{id}")
    @ResponseBody
    public EmployeeNoAdminDTO getEmployee(@PathVariable Integer id){
        EmployeeNoAdminDTO employee = employeeNAService.getEmployee(id);
        if(employee!=null) {
            logger.info("Employee found");
            return employee;
        }else {
            logger.info("No results found for id "+id);
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No results found for id"+id);
        }

    }

    @GetMapping("employee/birthday")
    @ResponseBody
    public EmployeeBirthdayDTO getEmployeesBirthday(){
        EmployeeBirthdayDTO ebp= new EmployeeBirthdayDTO();
        List<EmployeeNoAdminDTO> employeesBirthdayToday = employeeNAService.getTodayEmployeeBirthDay();
        List<EmployeeNoAdminDTO> employeesBirthdayNextWeek= employeeNAService.getNextWeekEmployeeBirthDay();
        logger.info("Birthdays found for today " + employeesBirthdayToday.size());
        logger.info("Birthdays found for next week " + employeesBirthdayNextWeek.size());
        if(employeesBirthdayToday.size()>0 || employeesBirthdayNextWeek.size()>0) {
            ebp.setCurrentWeek(employeesBirthdayToday);
            ebp.setNextWeek(employeesBirthdayNextWeek);
            return ebp;
        }else{
            logger.info("No birthdays found for this or next week");
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No birthdays found for this or next week");
        }
    }
}
