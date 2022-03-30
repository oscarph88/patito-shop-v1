package com.oscar.patito.service;

import com.oscar.patito.helper.EmployeeHelper;
import com.oscar.patito.helper.PositionHelper;
import com.oscar.patito.model.Employee;
import com.oscar.patito.model.Position;
import com.oscar.patito.model.PositionInfo;
import com.oscar.patito.payload.EmployeePayload;
import com.oscar.patito.payload.PositionInfoPayload;
import com.oscar.patito.payload.PositionPayload;
import com.oscar.patito.repository.EmployeeRepository;
import com.oscar.patito.repository.PositionInfoRepository;
import com.oscar.patito.repository.PositionsRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private PositionsRepository positionsRepository;
    @Autowired
    private PositionInfoRepository positionInfoRepository;

    private static final Logger logger = LogManager.getLogger(EmployeeService.class);
    EmployeeHelper eh= new EmployeeHelper();
    PositionHelper ph= new PositionHelper();

    public Employee saveEmployee(EmployeePayload emp)throws DataIntegrityViolationException {
        Employee employee= eh.generateEmployee(emp);
        logger.info("Saving employee "+emp.getFirstName());
        return employeeRepository.save(employee);
    }

    public List<Employee> saveEmployeeAll(List<EmployeePayload> employees)throws DataIntegrityViolationException {
        List<Employee> empSaved= new ArrayList<>();
        for(EmployeePayload emp: employees) {
            Employee employee= eh.generateEmployee(emp);
            logger.info("Saving employee "+emp.getFirstName());
            empSaved.add(employeeRepository.save(employee));
        }
        return empSaved;
    }

    public List<EmployeePayload> listEmployees(){
        List<Employee> employees = employeeRepository.findAll();
        logger.info("Employee list completed");
        return employees.stream().map(e -> new EmployeePayload(e.getId(),e.getCorporateEmail(), e.getFirstName(),
                e.getLastName(), e.getGender(), e.getActive(), eh.generateContactPayload(e.getContact()),
                eh.generatePositionInfoPayload(e.getPositionInfo(), getPosition(e.getPositionInfo().getOldPosition()),
                        getPosition(e.getPositionInfo().getCurrentPosition())))).collect(Collectors.toList());
    }

    public Position getPosition(Integer id){
        logger.info("Searching for position "+id);
        Optional<Position> position = positionsRepository.findById(id!=null?id:0);
        //return employee.isPresent()?eh.generateEmployeePayload(employee.get()):null;
        return position.map(value -> value).orElse(null);
    }

    public EmployeePayload listEmployee(Integer id){
        logger.info("Searching for employee "+id);
        Optional<Employee> employee = employeeRepository.findById(id);
        //return employee.isPresent()?eh.generateEmployeePayload(employee.get()):null;
        return employee.map(value -> eh.generateEmployeePayload(value)).orElse(null);
    }

    public Employee updateEmployee(EmployeePayload emp) throws DataIntegrityViolationException{
        Optional<Employee> optionalEmployee = employeeRepository.findById(emp.getId());
        if(optionalEmployee.isPresent()){
            Employee employee = eh.generateEmployeeToUpdate(optionalEmployee.get() , emp);
            return employeeRepository.save(employee);
        }else{
            logger.info("No results found for id "+emp.getId());
            throw new DataIntegrityViolationException("No results found for id "+emp.getId());
        }
    }

    public void deleteEmployee(Integer id) throws EmptyResultDataAccessException {
        logger.info("Deleting employee "+id);
        employeeRepository.deleteById(id);
    }

    public List<Position> savePositions(List<PositionPayload> positions)throws DataIntegrityViolationException {
        List<Position> positionSaved = new ArrayList<>();
        for(PositionPayload pos: positions) {
            Position position= ph.generatePositions(pos);
            logger.info("Saving position "+position.getDescription());
            positionSaved.add(positionsRepository.save(position));
        }
        return positionSaved;
    }

    public List<PositionPayload> listPositions(){
        List<Position> positions = positionsRepository.findAll();
        logger.info("Positions list completed");
        return positions.stream().map(p -> new PositionPayload(p.getId(), p.getName(), p.getDescription())).collect(Collectors.toList());
    }

    public List<PositionInfoPayload> listPositionsInfo(){
        List<PositionInfo> positionsInfo = positionInfoRepository.findAll();
        logger.info("Positions info list completed");
        return positionsInfo.stream().map(p -> new PositionInfoPayload(p.getCorporateEmail(), p.getOldPosition(),
                p.getOldPosition()!=null?getPosition(p.getOldPosition()).getName():null,
                p.getCurrentPosition(),
                p.getCurrentPosition()!=null?getPosition(p.getCurrentPosition()).getName():null,
                p.getOldSalary(), p.getCurrentSalary())).collect(Collectors.toList());
    }
}
