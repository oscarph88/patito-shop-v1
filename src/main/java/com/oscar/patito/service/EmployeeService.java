package com.oscar.patito.service;

import com.oscar.patito.dto.EmployeeDTO;
import com.oscar.patito.dto.PositionInfoDTO;
import com.oscar.patito.helper.EmployeeHelper;
import com.oscar.patito.helper.PositionHelper;
import com.oscar.patito.model.Employee;
import com.oscar.patito.model.Position;
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


    public EmployeeDTO saveEmployee(EmployeeDTO emp)throws DataIntegrityViolationException {
        if(emp.getPositionInfo()!=null) {
            emp.getPositionInfo().setCurrentPosition(ph.generatePositionPayload(getPosition(emp.getPositionInfo().getCurrentPosition().getId())));
        }else{
            PositionInfoDTO positionInfoDto= new PositionInfoDTO();
            positionInfoDto.setCurrentPosition(ph.generatePositionPayload(getPosition(1)));
            positionInfoDto.setCurrentSalary(0.00);
            emp.setPositionInfo(positionInfoDto);
        }
        emp.setActive(true);
        Employee employee= eh.generateEmployee(emp);
        logger.info("Saving employee "+emp.getFirstName());
        return eh.generateEmployeePayload(employeeRepository.save(employee));
    }

    public List<EmployeeDTO> saveEmployeeAll(List<EmployeeDTO> employees)throws DataIntegrityViolationException {
        List<Employee> empSaved= new ArrayList<>();
        for(EmployeeDTO emp: employees) {
            if(emp.getPositionInfo()!=null) {
                emp.getPositionInfo().setCurrentPosition(ph.generatePositionPayload(getPosition(emp.getPositionInfo().getCurrentPosition().getId())));
            }else{
                PositionInfoDTO positionInfoDto= new PositionInfoDTO();
                positionInfoDto.setCurrentPosition(ph.generatePositionPayload(getPosition(1)));
                positionInfoDto.setCurrentSalary(0.00);
                emp.setPositionInfo(positionInfoDto);
            }
            emp.setActive(true);
            Employee employee= eh.generateEmployee(emp);
            logger.info("Saving employee "+emp.getFirstName());
            empSaved.add(employeeRepository.save(employee));
        }
        //return empSaved;
        return empSaved.stream().map(e -> new EmployeeDTO(e.getId(),e.getCorporateEmail(), e.getFirstName(),
                        e.getLastName(), e.getGender(), e.getActive(),
                        eh.generateContactPayload(e.getContact()),
                        ph.generatePositionInfoPayload(e.getPositionInfo())))
                .collect(Collectors.toList());
    }

    public List<EmployeeDTO> listEmployees(){
        //List<Employee> employees = employeeRepository.findAll();
        List<Employee> employees = employeeRepository.findAllActive(true);
        logger.info("Employee list completed");
        return employees.stream().map(e -> new EmployeeDTO(e.getId(),e.getCorporateEmail(), e.getFirstName(),
                e.getLastName(), e.getGender(), e.getActive(),
                eh.generateContactPayload(e.getContact()),
                ph.generatePositionInfoPayload(e.getPositionInfo())))
                .collect(Collectors.toList());
    }

    public List<EmployeeDTO> filterEmployees(String firstname, String lastname, String position, Boolean all){
        List<Employee> employees =  all ? employeeRepository.findAll():employeeRepository.findAllActive(true);

        logger.info("Employee list completed");
        return employees.stream()
                .filter(t -> t.getFirstName().toLowerCase().contains(firstname!=null?firstname.toLowerCase():"") &&
                        t.getLastName().toLowerCase().contains(lastname!=null?lastname.toLowerCase():"") &&
                        t.getPositionInfo().getCurrentPosition().getName().toLowerCase().contains(position!=null?position.toLowerCase():""))
         .collect(Collectors.toList()).stream().map(e -> new EmployeeDTO(e.getId(),e.getCorporateEmail(), e.getFirstName(),
                        e.getLastName(), e.getGender(), e.getActive(),
                        eh.generateContactPayload(e.getContact()),
                        ph.generatePositionInfoPayload(e.getPositionInfo())))
                .collect(Collectors.toList());
    }


    public EmployeeDTO getEmployee(Integer id){
        logger.info("Searching for employee "+id);
        Optional<Employee> employee = employeeRepository.findById(id);
        return employee.map(value -> eh.generateEmployeePayload(value)).orElse(null);
    }

    public EmployeeDTO updateEmployee(EmployeeDTO emp) throws DataIntegrityViolationException{
        Optional<Employee> optionalEmployee = employeeRepository.findById(emp.getId());
        if(optionalEmployee.isPresent()){

            if(emp.getPositionInfo().getOldPosition()!=null) {
                optionalEmployee.get().getPositionInfo().setOldPosition(getPosition(emp.getPositionInfo().getOldPosition().getId()));
            }
            if(emp.getPositionInfo().getCurrentPosition()!=null) {
                optionalEmployee.get().getPositionInfo().setCurrentPosition(getPosition(emp.getPositionInfo().getCurrentPosition().getId()));
            }

            Employee employee = eh.generateEmployeeToUpdate(optionalEmployee.get() , emp);
            return eh.generateEmployeePayload(employeeRepository.save(employee));
        }else{
            logger.info("No results found for id "+emp.getId());
            throw new DataIntegrityViolationException("No results found for id "+emp.getId());
        }
    }

    public void deleteEmployee(Integer id) throws EmptyResultDataAccessException {
        logger.info("Deleting employee "+id);
        employeeRepository.deleteById(id);
    }

    public EmployeeDTO assignEmployee(EmployeeDTO emp) throws DataIntegrityViolationException{
        Optional<Employee> optionalEmployee = employeeRepository.findById(emp.getId());
        if(optionalEmployee.isPresent()){

            if(emp.getPositionInfo().getOldPosition()!=null) {
                optionalEmployee.get().getPositionInfo().setOldPosition(getPosition(emp.getPositionInfo().getOldPosition().getId()));
            }
            if(emp.getPositionInfo().getCurrentPosition()!=null) {
                optionalEmployee.get().getPositionInfo().setCurrentPosition(getPosition(emp.getPositionInfo().getCurrentPosition().getId()));
            }

            //Employee employee = eh.generateEmployeeToUpdate(optionalEmployee.get() , emp);
            Employee employee = eh.generateEmployeeToAssign(optionalEmployee.get() , emp);
            return eh.generateEmployeePayload(employeeRepository.save(employee));
        }else{
            logger.info("No results found for id "+emp.getId());
            throw new DataIntegrityViolationException("No results found for id "+emp.getId());
        }
    }

    public EmployeeDTO softDeleteEmployee(Integer id){
        logger.info("Searching for employee to delete: "+id);
        Optional<Employee> employee = employeeRepository.findById(id);
        if(employee.isPresent()){
            employee.get().setActive(false);
            employee.get().getPositionInfo().setActive(false);
            return eh.generateEmployeePayload(employeeRepository.save(employee.get()));
        }else{
            return null;
        }
    }

    public Position getPosition(Integer id){
        logger.info("Searching for position "+id);
        Optional<Position> position = positionRepository.findById(id!=null?id:0);
        return position.map(value -> value).orElse(null);
    }

    public List<EmployeeDTO> reportEmployees(String country, String state){
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
        return employees.stream().map(e -> new EmployeeDTO(e.getId(),e.getCorporateEmail(), e.getFirstName(),
                        e.getLastName(), e.getGender(), e.getActive(),
                        eh.generateContactPayload(e.getContact()),
                        ph.generatePositionInfoPayload(e.getPositionInfo())))
                .collect(Collectors.toList());
    }
}
