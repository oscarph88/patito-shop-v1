package com.oscar.patito.helper;

import com.oscar.patito.model.Contact;
import com.oscar.patito.model.Employee;
import com.oscar.patito.model.Position;
import com.oscar.patito.model.PositionInfo;
import com.oscar.patito.payload.ContactPayload;
import com.oscar.patito.payload.EmployeePayload;
import com.oscar.patito.payload.PositionInfoPayload;
import com.oscar.patito.payload.PositionPayload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EmployeeHelper {

    private static final Logger logger = LogManager.getLogger(EmployeeHelper.class);
    public Employee generateEmployee(EmployeePayload ep){
        Employee employee = new Employee();
        Contact contact= new Contact();
        PositionInfo positionInfo = new PositionInfo();
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
        positionInfo.setCorporateEmail(ep.getCorporateEmail());
        if (ep.getPosition() != null) {
            positionInfo.setOldPosition(ep.getPosition().getOldPosition());
            positionInfo.setCurrentPosition(ep.getPosition().getCurrentPosition());
            positionInfo.setOldSalary(ep.getPosition().getOldSalary());
            positionInfo.setCurrentSalary(ep.getPosition().getCurrentSalary());
        }
        employee.setContact(contact);
        employee.setPositionInfo(positionInfo);
        logger.info("Employee object created");
        return employee;
    }

    public EmployeePayload generateEmployeePayload(Employee em){
        EmployeePayload employeePayload = new EmployeePayload();
        ContactPayload contact= new ContactPayload();
        PositionInfoPayload positionInfo = new PositionInfoPayload();
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
        positionInfo.setCorporateEmail(em.getCorporateEmail());
        positionInfo.setOldPosition(em.getPositionInfo().getOldPosition());
        positionInfo.setCurrentPosition(em.getPositionInfo().getCurrentPosition());
        positionInfo.setOldSalary(em.getPositionInfo().getOldSalary());
        positionInfo.setCurrentSalary(em.getPositionInfo().getCurrentSalary());
        employeePayload.setContact(contact);
        employeePayload.setPosition(positionInfo);
        logger.info("Employee object created");
        return employeePayload;
    }

    public Employee generateEmployeeToUpdate(Employee employee, EmployeePayload ep) {
        Contact contact = employee.getContact();
        PositionInfo positionInfo = employee.getPositionInfo();
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
        positionInfo.setOldPosition(ep.getPosition().getOldPosition() != null ? ep.getPosition().getOldPosition() : positionInfo.getOldPosition());
        positionInfo.setCurrentPosition(ep.getPosition().getCurrentPosition() != null ? ep.getPosition().getCurrentPosition() : positionInfo.getCurrentPosition());
        positionInfo.setOldSalary(ep.getPosition().getOldSalary() != null ? ep.getPosition().getOldSalary() : positionInfo.getOldSalary());
        positionInfo.setCurrentSalary(ep.getPosition().getCurrentSalary() != null ? ep.getPosition().getCurrentSalary() : positionInfo.getCurrentSalary());
        employee.setContact(contact);
        employee.setPositionInfo(positionInfo);
        logger.info("Employee object updated");
        return employee;
    }

    public ContactPayload generateContactPayload(Contact c){
        return new ContactPayload(c.getCorporateEmail(), c.getPersonalEmail(), c.getPhoneNumber(),
                c.getAddress(),c.getCity(), c.getState(), c.getCountry(), c.getBirthday());
    }

    public PositionInfoPayload generatePositionInfoPayload(PositionInfo p, Position oldPosition, Position currentPosition){
        return new PositionInfoPayload(p.getCorporateEmail(), p.getOldPosition(),
                oldPosition!=null?oldPosition.getName():null, p.getCurrentPosition(),
                currentPosition!=null?currentPosition.getName():null, p.getOldSalary(), p.getCurrentSalary());
    }

    public PositionPayload generatePositionPayload(Position p){
        return new PositionPayload(p.getId(), p.getName(), p.getDescription());
    }

    public PositionInfo generatePositionInfo(PositionInfoPayload p){
        return new PositionInfo(p.getCorporateEmail(), p.getOldPosition(), p.getCurrentPosition(),
                p.getOldSalary(), p.getCurrentSalary());
    }

    public Position generatePosition(PositionPayload p){
        return new Position(p.getId(), p.getName(), p.getDescription());
    }
}
