package com.oscar.patito.api;

import com.oscar.patito.enums.SalaryRangeEnum;
import com.oscar.patito.helper.EmployeeHelper;
import com.oscar.patito.model.Employee;
import com.oscar.patito.payload.EmployeePayload;
import com.oscar.patito.payload.PositionInfoPayload;
import com.oscar.patito.payload.PositionPayload;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/patito/admin")
public class PatitoRestApi {

    private static final Logger logger = LogManager.getLogger(PatitoRestApi.class);

    @Autowired
    EmployeeService employeeService;
    @Autowired
    PositionService positionService;

    EmployeeHelper empHelper= new EmployeeHelper();

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

    @GetMapping("getEmployee")
    public EmployeePayload getEmployee(@RequestParam("id") Integer id){
        EmployeePayload employee = employeeService.getEmployee(id);
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
        if (positionsInfo.size() > 0) {
            return positionsInfo;
        } else {
            logger.info("No positions info found");
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No positions info found");
        }
    }

    @GetMapping("listEmployeesPositions")
    public Map<String, Long>listEmployeesPositions() {
        List<PositionInfoPayload> positionsInfo = positionService.listPositionsInfo();
        List<PositionPayload> currentPositions= new ArrayList<>();
        logger.info("Positions info found " + positionsInfo.size());
        if (positionsInfo.size() > 0) {
            for(PositionInfoPayload pip: positionsInfo){
                    currentPositions.add(pip.getCurrentPosition());
                }
            logger.info("Current positions assigned to employees " + currentPositions.stream().distinct().count());
            Map<String, Long> countForPosition = currentPositions.stream()
                    .collect(Collectors.groupingBy(PositionPayload::getName, Collectors.counting()));
            return countForPosition;
            }else {
            logger.info("No current positions info found");
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No current positions info found");
        }
    }

    @GetMapping("graphGenders")
    public Map<String, String>graphGenders() {

        List<EmployeePayload> employees = employeeService.listEmployees();
        logger.info("Employees found " + employees.size());
        if(employees.size()>0) {
            Map<String, Long> countForGender = employees.stream()
                    .collect(Collectors.groupingBy(EmployeePayload::getGender, (Collectors.counting())));
            Map<String, String> percentForGender = countForGender.entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getKey,
                            entry -> empHelper.calculatePercentage(entry.getValue(), employees.size())+" %"));
            return percentForGender;
        }else{
            logger.info("No genders found");
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No genders found");
        }
    }

    @GetMapping("graphSalary")
    public Map<SalaryRangeEnum,String>graphSalary() {

        List<PositionInfoPayload> positionsInfo = positionService.listPositionsInfo();

        //List<EmployeePayload> employees = employeeService.listEmployees();
        logger.info("Positions found " + positionsInfo.size());
        if(positionsInfo.size()>0) {
            Map<SalaryRangeEnum,List<PositionInfoPayload>> positionsRange = positionsInfo.stream()
                    .collect(Collectors.groupingBy(EmployeeHelper::getRange));
            Map<SalaryRangeEnum, String> percentForSalary = positionsRange.entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getKey,
                            entry -> empHelper.calculatePercentage(entry.getValue().size(), positionsInfo.size())+" %"));
            return percentForSalary;
        }else{
            logger.info("No positions found");
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No positions found");
        }
    }

    @GetMapping("reportEmployees")
    public List<EmployeePayload> reportEmployees(@RequestParam(required = false) String country, @RequestParam(required = false) String state){
        List<EmployeePayload> employees;
        if(country==null && state ==null) {
            employees = employeeService.listEmployees();
        }else{
            employees = employeeService.reportEmployees(country, state);
        }
        logger.info("Employees found " + employees.size());
        if(employees.size()>0) {
            return employees;
        }else{
            logger.info("No employees found");
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No employees found");
        }
    }

    @GetMapping("filterEmployees")
    public List<EmployeePayload> filterEmployees(@RequestParam(required = false) String firstName,
                                                 @RequestParam(required = false) String lastName,
                                                 @RequestParam(required = false) String position,
                                                 @RequestParam(required = false) boolean deleted){
        logger.info("Searching for " + firstName +" , "+lastName +" , "+ position +" , "+ deleted);
        List<EmployeePayload> employees = employeeService.filterEmployees(firstName, lastName, position, deleted);
        logger.info("Employees found " + employees.size());
        if(employees.size()>0) {
            return employees;
        }else{
            logger.info("No employees found");
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No employees found");
        }
    }
}
