package com.oscar.patito.api;

import com.oscar.patito.dto.EmployeeDTO;
import com.oscar.patito.dto.PositionInfoDTO;
import com.oscar.patito.dto.PositionInfoHistoryDTO;
import com.oscar.patito.enums.SalaryRangeEnum;
import com.oscar.patito.helper.EmployeeHelper;
import com.oscar.patito.service.EmployeeService;
import com.oscar.patito.service.PositionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/patito/report")
public class ReportApi {

    private static final Logger logger = LogManager.getLogger(ReportApi.class);
    @Autowired
    PositionService positionService;
    @Autowired
    EmployeeService employeeService;

    EmployeeHelper empHelper= new EmployeeHelper();

    @GetMapping("employees/assigned-positions-report")
    @ResponseBody
    public List<PositionInfoDTO> listPositionsInfo() {
        List<PositionInfoDTO> positionsInfo = positionService.listPositionsInfo();
        logger.info("Positions info found " + positionsInfo.size());
        if (positionsInfo.size() > 0) {
            return positionsInfo;
        } else {
            logger.info("No positions info found");
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No positions info found");
        }
    }

    @GetMapping("employees/positions-history")
    @ResponseBody
    public List<PositionInfoHistoryDTO> listPositionsInfoHistory() {
        List<PositionInfoHistoryDTO> positionsInfoHistory = positionService.listPositionsInfoHistory();
        logger.info("Positions history info found " + positionsInfoHistory.size());
        if (positionsInfoHistory.size() > 0) {
            return positionsInfoHistory;
        } else {
            logger.info("No positions history info found");
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No positions history info found");
        }
    }

    @GetMapping("employees/positions")
    @ResponseBody
    public Map<String, Long> listEmployeesPositions() {
        List<PositionInfoDTO> positionsInfo = positionService.listPositionsInfo();
        //List<PositionDTO> currentPositions= new ArrayList<>();
        logger.info("Positions info found " + positionsInfo.size());
        if (positionsInfo.size() > 0) {
            return empHelper.getEmployeesPositions(positionsInfo);
        }else {
            logger.info("No current positions info found");
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No current positions info found");
        }
    }

    @GetMapping("employees/graph-genders")
    @ResponseBody
    public Map<String, String>graphGenders() {

        List<EmployeeDTO> employees = employeeService.listEmployees();
        logger.info("Employees found " + employees.size());
        if(employees.size()>0) {
            return empHelper.getCountGender(employees);
        }else{
            logger.info("No genders found");
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No genders found");
        }
    }

    @GetMapping("employees/graph-salary")
    @ResponseBody
    public Map<SalaryRangeEnum,String>graphSalary() {

        List<PositionInfoDTO> positionsInfo = positionService.listPositionsInfo();
        logger.info("Positions found " + positionsInfo.size());
        if(positionsInfo.size()>0) {
            return empHelper.getPercentSalary(positionsInfo);
        }else{
            logger.info("No positions found");
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No positions found");
        }
    }

    @GetMapping("employees/country-state-report")
    @ResponseBody
    public List<EmployeeDTO> reportEmployeesCountryState(@RequestParam(required = false) String country, @RequestParam(required = false) String state){
        List<EmployeeDTO> employees;
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
}
