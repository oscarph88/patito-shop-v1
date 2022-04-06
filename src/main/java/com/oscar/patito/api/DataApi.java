package com.oscar.patito.api;

import com.oscar.patito.dto.EmployeeDTO;
import com.oscar.patito.dto.PositionDTO;
import com.oscar.patito.model.Position;
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
@RequestMapping("api/patito/data")
public class DataApi {

    private static final Logger logger = LogManager.getLogger(DataApi.class);

    @Autowired
    EmployeeService employeeService;
    @Autowired
    PositionService positionService;

    @PostMapping("employees/load")
    public String loadEmployees(@RequestBody List<EmployeeDTO> employeeRequests){
        try {
            List<EmployeeDTO> employees = employeeService.saveEmployeeAll(employeeRequests);
            return "Entries created " + employees.size();
        }catch(DataIntegrityViolationException d){
            logger.error("Employees not created correctly");
            logger.error(d.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Employees not created correctly", d);
        }
    }

    @PostMapping("positions/load")
    public String loadPositions(@RequestBody List<PositionDTO> positionsRequests){
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

    @GetMapping("positions")
    public List<PositionDTO> listPositions(){
        List<PositionDTO> positions = positionService.listPositions();
        logger.info("Positions found " + positions.size());
        if(positions.size()>0) {
            return positions;
        }else{
            logger.info("No positions found");
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No positions found");
        }
    }
}
