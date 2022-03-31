package com.oscar.patito.api;

import com.oscar.patito.model.Employee;
import com.oscar.patito.model.Position;
import com.oscar.patito.payload.EmployeePayload;
import com.oscar.patito.payload.PositionPayload;
import com.oscar.patito.service.EmployeeService;
import com.oscar.patito.service.PositionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("api/patito/mgmt")
public class DataMgmtApi {

    private static final Logger logger = LogManager.getLogger(DataMgmtApi.class);

    @Autowired
    EmployeeService employeeService;
    @Autowired
    PositionService positionService;

    @GetMapping("printTest")
    public String printTest() {
        return "Hello!";
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

    @PostMapping("loadPositions")
    public String loadPositions(@RequestBody List<PositionPayload> positionsRequests){
        try {
            List<Position> positions = positionService.savePositions(positionsRequests);
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
        List<PositionPayload> positions = positionService.listPositions();
        logger.info("Positions found " + positions.size());
        if(positions.size()>0) {
            return positions;
        }else{
            logger.info("No positions found");
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No positions found");
        }
    }
}
