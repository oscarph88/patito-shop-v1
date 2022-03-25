package com.oscar.patito.helper;

import com.oscar.patito.model.Contact;
import com.oscar.patito.model.Employee;
import com.oscar.patito.model.Position;
import com.oscar.patito.payload.EmployeePayload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EmployeeHelper {

    private static final Logger logger = LogManager.getLogger(EmployeeHelper.class);
    public Employee generateEmployee(EmployeePayload ep){
        Employee employee = new Employee();
        Contact contact= new Contact();
        Position position = new Position();
        employee.setId(ep.getId()!=null?ep.getId():null);
        employee.setCorporateEmail(ep.getCorporateEmail());
        employee.setFirstName(ep.getFirstName());
        employee.setLastName(ep.getLastName());
        employee.setGender(ep.getGender());
        employee.setActive(ep.getActive());
        contact.setCorporateEmail(ep.getCorporateEmail());
        contact.setPersonalEmail(ep.getContact().getPersonalEmail());
        contact.setPhoneNumber(ep.getContact().getPhoneNumber());
        contact.setAddress(ep.getContact().getAddress());
        contact.setCity(ep.getContact().getCity());
        contact.setState(ep.getContact().getState());
        contact.setCountry(ep.getContact().getCountry());
        contact.setBirthday(ep.getContact().getBirthday());
        position.setCorporateEmail(ep.getCorporateEmail());
        position.setOldPosition(ep.getPosition().getOldPosition());
        position.setCurrentPosition(ep.getPosition().getCurrentPosition());
        position.setOldSalary(ep.getPosition().getOldSalary());
        position.setCurrentSalary(ep.getPosition().getCurrentSalary());
        employee.setContact(contact);
        employee.setPosition(position);
        logger.info("Employee object created");
        return employee;
    }

    public EmployeePayload generateEmployeePayload(Employee em){
        EmployeePayload employeePayload = new EmployeePayload();
        Contact contact= new Contact();
        Position position = new Position();
        employeePayload.setId(em.getId());
        employeePayload.setCorporateEmail(em.getCorporateEmail());
        employeePayload.setFirstName(em.getFirstName());
        employeePayload.setLastName(em.getLastName());
        employeePayload.setGender(em.getGender());
        employeePayload.setActive(em.getActive());
        contact.setCorporateEmail(em.getCorporateEmail());
        contact.setPersonalEmail(em.getContact().getPersonalEmail());
        contact.setPhoneNumber(em.getContact().getPhoneNumber());
        contact.setAddress(em.getContact().getAddress());
        contact.setCity(em.getContact().getCity());
        contact.setState(em.getContact().getState());
        contact.setCountry(em.getContact().getCountry());
        contact.setBirthday(em.getContact().getBirthday());
        position.setCorporateEmail(em.getCorporateEmail());
        position.setOldPosition(em.getPosition().getOldPosition());
        position.setCurrentPosition(em.getPosition().getCurrentPosition());
        position.setOldSalary(em.getPosition().getOldSalary());
        position.setCurrentSalary(em.getPosition().getCurrentSalary());
        employeePayload.setContact(contact);
        employeePayload.setPosition(position);
        logger.info("Employee object created");
        return employeePayload;
    }

    public Employee generateEmployeeToUpdate(Employee employee, EmployeePayload ep) {
        Contact contact = employee.getContact();
        Position position = employee.getPosition();
        employee.setFirstName(ep.getFirstName() != null ? ep.getFirstName() : employee.getFirstName());
        employee.setLastName(ep.getLastName() != null ? ep.getLastName() : employee.getLastName());
        employee.setGender(ep.getGender() != null ? ep.getGender() : employee.getGender());
        employee.setActive(ep.getActive() != null ? ep.getActive() : employee.getActive());
        contact.setPersonalEmail(ep.getContact().getPersonalEmail() != null ? ep.getContact().getPersonalEmail() : contact.getPersonalEmail());
        contact.setPhoneNumber(ep.getContact().getPhoneNumber() != null ? ep.getContact().getPhoneNumber() : contact.getPhoneNumber());
        contact.setAddress(ep.getContact().getAddress() != null ? ep.getContact().getAddress() : contact.getAddress());
        contact.setCity(ep.getContact().getCity() != null ? ep.getContact().getCity() : contact.getCity());
        contact.setState(ep.getContact().getState() != null ? ep.getContact().getState() : contact.getState());
        contact.setCountry(ep.getContact().getCountry() != null ? ep.getContact().getCountry() : contact.getCountry());
        contact.setBirthday(ep.getContact().getBirthday() != null ? ep.getContact().getBirthday() : contact.getBirthday());
        position.setOldPosition(ep.getPosition().getOldPosition() != null ? ep.getPosition().getOldPosition() : position.getOldPosition());
        position.setCurrentPosition(ep.getPosition().getCurrentPosition() != null ? ep.getPosition().getCurrentPosition() : position.getCurrentPosition());
        position.setOldSalary(ep.getPosition().getOldSalary() != null ? ep.getPosition().getOldSalary() : position.getOldSalary());
        position.setCurrentSalary(ep.getPosition().getCurrentSalary() != null ? ep.getPosition().getCurrentSalary() : position.getCurrentSalary());
        employee.setContact(contact);
        employee.setPosition(position);
        logger.info("Employee object updated");
        return employee;
    }
}
