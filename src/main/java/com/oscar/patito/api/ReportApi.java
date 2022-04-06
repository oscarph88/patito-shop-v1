package com.oscar.patito.api;

import com.oscar.patito.dto.EmployeeDTO;
import com.oscar.patito.dto.PositionDTO;
import com.oscar.patito.dto.PositionInfoDTO;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @GetMapping("employees/positions")
    @ResponseBody
    public Map<String, Long> listEmployeesPositions() {
        List<PositionInfoDTO> positionsInfo = positionService.listPositionsInfo();
        List<PositionDTO> currentPositions= new ArrayList<>();
        logger.info("Positions info found " + positionsInfo.size());
        if (positionsInfo.size() > 0) {
            for(PositionInfoDTO pip: positionsInfo){
                currentPositions.add(pip.getCurrentPosition());
            }
            logger.info("Current positions assigned to employees " + currentPositions.stream().distinct().count());
            Map<String, Long> countForPosition = currentPositions.stream()
                    .collect(Collectors.groupingBy(PositionDTO::getName, Collectors.counting()));
            return countForPosition;
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
            Map<String, Long> countForGender = employees.stream()
                    .collect(Collectors.groupingBy(EmployeeDTO::getGender, (Collectors.counting())));
            Map<String, String> percentForGender = countForGender.entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getKey,
                            entry -> empHelper.calculatePercentage(entry.getValue(), employees.size())+" %"));
            return percentForGender;
        }else{
            logger.info("No genders found");
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No genders found");
        }
    }

    @GetMapping("employees/graph-salary")
    @ResponseBody
    public Map<SalaryRangeEnum,String>graphSalary() {

        List<PositionInfoDTO> positionsInfo = positionService.listPositionsInfo();

        //List<EmployeePayload> employees = employeeService.listEmployees();
        logger.info("Positions found " + positionsInfo.size());
        if(positionsInfo.size()>0) {
            Map<SalaryRangeEnum,List<PositionInfoDTO>> positionsRange = positionsInfo.stream()
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
