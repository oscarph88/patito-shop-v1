package com.oscar.patito.service;

import com.oscar.patito.helper.EmployeeHelper;
import com.oscar.patito.model.Employee;
import com.oscar.patito.dto.EmployeeNoAdminDTO;
import com.oscar.patito.repository.EmployeeRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeNoAdminService {

    @Autowired
    private EmployeeRepository employeeRepository;

    private static final Logger logger = LogManager.getLogger(EmployeeNoAdminService.class);
    private EmployeeHelper eh= new EmployeeHelper();

    public List<EmployeeNoAdminDTO> listEmployees(){
        //List<Employee> employees = employeeRepository.findAll();
        List<Employee> employees = employeeRepository.findAllActive(true);
        logger.info("Employee list completed");
        return employees.stream().map(e -> new EmployeeNoAdminDTO(e.getId(),e.getCorporateEmail(), e.getFirstName(),
                        e.getLastName(), e.getGender(), eh.generateContactPayload(e.getContact()),
                        e.getPositionInfo().getCurrentPosition().getName()))
                .collect(Collectors.toList());
    }

    public List<EmployeeNoAdminDTO> filterEmployees(String firstname, String lastname, String position){
        //List<Employee> employees = employeeRepository.findAll();
        List<Employee> employees = employeeRepository.findAllActive(true);
        logger.info("Employee list completed");
        return employees.stream()
                .filter(t -> t.getFirstName().toLowerCase().contains(firstname!=null?firstname.toLowerCase():"") &&
                        t.getLastName().toLowerCase().contains(lastname!=null?lastname.toLowerCase():"") &&
                        t.getPositionInfo().getCurrentPosition().getName().toLowerCase().contains(position!=null?position.toLowerCase():""))
                .collect(Collectors.toList()).stream().map(e -> new EmployeeNoAdminDTO(e.getId(),e.getCorporateEmail(), e.getFirstName(),
                        e.getLastName(), e.getGender(), eh.generateContactPayload(e.getContact()),
                        e.getPositionInfo().getCurrentPosition().getName()))
                .collect(Collectors.toList());
    }

    public EmployeeNoAdminDTO getEmployee(int id){
        logger.info("Searching for employee "+id);
        Employee employee = employeeRepository.findActiveEmployee(true, id);
        //return employee.map(value -> eh.generateEmployeePayload(value)).orElse(null);
        if(employee!=null) {
            return new EmployeeNoAdminDTO(employee.getId(), employee.getCorporateEmail(), employee.getFirstName(),
                    employee.getLastName(), employee.getGender(), eh.generateContactPayload(employee.getContact()),
                    employee.getPositionInfo().getCurrentPosition().getName());
        }else{
            return null;
        }
    }

    public List<EmployeeNoAdminDTO> getTodayEmployeeBirthDay(){
        logger.info("Searching for today birthday employee ");
        LocalDateTime today = LocalDateTime.now().with(LocalTime.of(0, 0));
        LocalDateTime tomorrow = today.plusDays(1);
        List<Employee> employees = employeeRepository.findTodayEmployeeBirthdate2(true, today);
        //List<Employee> employees = employeeRepository.findTodayEmployeeBirthdate(true, today, tomorrow);
        logger.info("Employee today birthday list completed "+today+" to "+tomorrow);
        return employees.stream().map(e -> new EmployeeNoAdminDTO(e.getId(),e.getCorporateEmail(), e.getFirstName(),
                        e.getLastName(), e.getGender(), eh.generateContactPayload(e.getContact()),
                        e.getPositionInfo().getCurrentPosition().getName()))
                .collect(Collectors.toList());
    }

    public List<EmployeeNoAdminDTO> getNextWeekEmployeeBirthDay(){
        logger.info("Searching for next week birthday employee ");
        LocalDateTime startDay = LocalDateTime.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).with(LocalTime.of(0, 0));
        LocalDateTime endDay = startDay.with(TemporalAdjusters.next(DayOfWeek.SUNDAY)).with(LocalTime.of(23,59,59));
        List<Employee> employees = employeeRepository.findNextWeekEmployeeBirthdate2(true, startDay, endDay);
        //List<Employee> employees = employeeRepository.findNextWeekEmployeeBirthdate(true, startDay, endDay);
        logger.info("Employee today birthday list completed from "+startDay+" to "+endDay);
        return employees.stream().map(e -> new EmployeeNoAdminDTO(e.getId(),e.getCorporateEmail(), e.getFirstName(),
                        e.getLastName(), e.getGender(), eh.generateContactPayload(e.getContact()),
                        e.getPositionInfo().getCurrentPosition().getName()))
                .collect(Collectors.toList());
    }
}
