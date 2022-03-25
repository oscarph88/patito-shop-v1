package com.oscar.patito.api;

import com.oscar.patito.model.Employee;
import com.oscar.patito.payload.EmployeePayload;
import com.oscar.patito.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("api/patito/admin")
public class PatitoRestApi {

    @Autowired
    EmployeeService employeeService;

    @GetMapping("printTest")
    public String printTest() {
        return "Hello!";
    }

    @PostMapping("saveEmployee")
    public String saveEmployee(@RequestBody EmployeePayload employeeRequest){
        try {
            Employee employee = employeeService.saveEmployee(employeeRequest);
            return"Save Success with " + employee.getCorporateEmail();
        }catch(DataIntegrityViolationException d){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Employee not created correctly", d);
        }
    }

    @PostMapping("loadEmployees")
    public String loadEmployees(@RequestBody List<EmployeePayload> employeeRequests){
        try {
            List<Employee> employees = employeeService.saveEmployeeAll(employeeRequests);
            return "Entries created " + employees.size();
        }catch(DataIntegrityViolationException d){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Employees not created correctly", d);
        }
    }

    @GetMapping("listEmployees")
    public List<EmployeePayload> listEmployees(){
        List<EmployeePayload> employees = employeeService.listEmployees();
        System.out.println(employees.size() + " Employees");
                return employees;
    }

    @GetMapping("listEmployee")
    public EmployeePayload listEmployee(@RequestParam("id") Integer id){
        EmployeePayload employee = employeeService.listEmployee(id);
        System.out.println(employee + " Employees");
        //return employee!=null?employee;
            if(employee!=null) {
                return employee;
            }else {
                throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No results found for "+id);
            }

    }

    @PutMapping("updateEmployee")
    public String updateEmployee(@RequestBody EmployeePayload employeeRequest){
        Employee employee= employeeService.updateEmployee(employeeRequest);
        return "Update Success with " + employee.getCorporateEmail() + employee.getId();
    }

    @DeleteMapping("deleteEmployee")
    public String DeleteEmployee(@RequestParam("id") Integer id){
        employeeService.deleteEmployee(id);
        return "Employee deleted "+id;
    }

}
