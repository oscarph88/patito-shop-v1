package com.oscar.patito.helper;

import com.oscar.patito.model.Contact;
import com.oscar.patito.model.Employee;
import com.oscar.patito.model.Position;
import com.oscar.patito.payload.EmployeePayload;

public class EmployeeHelper {

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
        employee.setContact(contact);
        employee.setPosition(position);
        System.out.println("Employee object created");
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
        employeePayload.setContact(contact);
        employeePayload.setPosition(position);
        System.out.println("Employee object created");
        return employeePayload;
    }
}
