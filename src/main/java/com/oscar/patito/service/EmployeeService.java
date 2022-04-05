package com.oscar.patito.service;

import com.oscar.patito.helper.EmployeeHelper;
import com.oscar.patito.helper.PositionHelper;
import com.oscar.patito.model.Employee;
import com.oscar.patito.model.Position;
import com.oscar.patito.payload.EmployeePayload;
import com.oscar.patito.repository.EmployeeRepository;
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
    private PositionsRepository positionRepository;


    private static final Logger logger = LogManager.getLogger(EmployeeService.class);
    private EmployeeHelper eh= new EmployeeHelper();
    private PositionHelper ph = new PositionHelper();

    public Employee saveEmployee(EmployeePayload emp)throws DataIntegrityViolationException {
        emp.getPosition().setCurrentPosition(ph.generatePositionPayload(getPosition(emp.getPosition().getCurrentPosition().getId())));
        Employee employee= eh.generateEmployee(emp);
        logger.info("Saving employee "+emp.getFirstName());
        return employeeRepository.save(employee);
    }

    public List<Employee> saveEmployeeAll(List<EmployeePayload> employees)throws DataIntegrityViolationException {
        List<Employee> empSaved= new ArrayList<>();
        for(EmployeePayload emp: employees) {
            emp.getPosition().setCurrentPosition(ph.generatePositionPayload(getPosition(emp.getPosition().getCurrentPosition().getId())));
            Employee employee= eh.generateEmployee(emp);
            logger.info("Saving employee "+emp.getFirstName());
            empSaved.add(employeeRepository.save(employee));
        }
        return empSaved;
    }

    public List<EmployeePayload> listEmployees(){
        //List<Employee> employees = employeeRepository.findAll();
        List<Employee> employees = employeeRepository.findAllActive(true);
        logger.info("Employee list completed");
        return employees.stream().map(e -> new EmployeePayload(e.getId(),e.getCorporateEmail(), e.getFirstName(),
                e.getLastName(), e.getGender(), e.getActive(),
                eh.generateContactPayload(e.getContact()),
                ph.generatePositionInfoPayload(e.getPositionInfo())))
                .collect(Collectors.toList());
    }

    public List<EmployeePayload> filterEmployees(String firstname, String lastname, String position, Boolean all){
        List<Employee> employees =  all ? employeeRepository.findAll():employeeRepository.findAllActive(true);

        logger.info("Employee list completed");
        return employees.stream()
                .filter(t -> t.getFirstName().toLowerCase().contains(firstname!=null?firstname.toLowerCase():"") &&
                        t.getLastName().toLowerCase().contains(lastname!=null?lastname.toLowerCase():"") &&
                        t.getPositionInfo().getCurrentPosition().getName().toLowerCase().contains(position!=null?position.toLowerCase():""))
         .collect(Collectors.toList()).stream().map(e -> new EmployeePayload(e.getId(),e.getCorporateEmail(), e.getFirstName(),
                        e.getLastName(), e.getGender(), e.getActive(),
                        eh.generateContactPayload(e.getContact()),
                        ph.generatePositionInfoPayload(e.getPositionInfo())))
                .collect(Collectors.toList());
    }


    public EmployeePayload getEmployee(Integer id){
        logger.info("Searching for employee "+id);
        Optional<Employee> employee = employeeRepository.findById(id);
        return employee.map(value -> eh.generateEmployeePayload(value)).orElse(null);
    }

    public Employee updateEmployee(EmployeePayload emp) throws DataIntegrityViolationException{
        Optional<Employee> optionalEmployee = employeeRepository.findById(emp.getId());
        if(optionalEmployee.isPresent()){

            if(emp.getPosition().getOldPosition()!=null) {
                optionalEmployee.get().getPositionInfo().setOldPosition(getPosition(emp.getPosition().getOldPosition().getId()));
            }
            if(emp.getPosition().getCurrentPosition()!=null) {
                optionalEmployee.get().getPositionInfo().setCurrentPosition(getPosition(emp.getPosition().getCurrentPosition().getId()));
            }

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

    public Employee softDeleteEmployee(Integer id){
        logger.info("Searching for employee to delete: "+id);
        Optional<Employee> employee = employeeRepository.findById(id);
        if(employee.isPresent()){
            employee.get().setActive(false);
            employee.get().getPositionInfo().setActive(false);
            return employeeRepository.save(employee.get());
        }else{
            return null;
        }
    }

    public Position getPosition(Integer id){
        logger.info("Searching for position "+id);
        Optional<Position> position = positionRepository.findById(id!=null?id:0);
        return position.map(value -> value).orElse(null);
    }

    public List<EmployeePayload> reportEmployees(String country, String state){
        List<Employee> employees;
        if(country!=null && state!=null) {
            employees = employeeRepository.findAllActiveCountryState(true, country, state);
        }else{
            if(country!=null){
                employees = employeeRepository.findAllActiveCountry(true, country);
            }else{
                employees = employeeRepository.findAllActiveState(true, state);
            }
        }
        logger.info("Employee list completed for country "+country+" and state "+state);
        return employees.stream().map(e -> new EmployeePayload(e.getId(),e.getCorporateEmail(), e.getFirstName(),
                        e.getLastName(), e.getGender(), e.getActive(),
                        eh.generateContactPayload(e.getContact()),
                        ph.generatePositionInfoPayload(e.getPositionInfo())))
                .collect(Collectors.toList());
    }
}
