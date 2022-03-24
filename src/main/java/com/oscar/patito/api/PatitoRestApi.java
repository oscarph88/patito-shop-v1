package com.oscar.patito.api;

import com.oscar.patito.model.Employee;
import com.oscar.patito.payload.EmployeePayload;
import com.oscar.patito.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/patito")
public class PatitoRestApi {

    @Autowired
    EmployeeService employeeService;

    @GetMapping("printTest")
    public String printTest() {
        return "Hello!";
    }

    @PostMapping("saveEmployee")
    public String saveEmployee(@RequestBody EmployeePayload employeeRequest){
        Employee employee= employeeService.saveEmployee(employeeRequest);
        return "Save Success with " + employee.getCorporateEmail();
    }

    @GetMapping("listEmployees")
    public List<EmployeePayload> listEmployees(){
        List<EmployeePayload> employees = employeeService.listEmployees();
        System.out.println(employees.size() + " Employees");
                return employees;
    }

    @PutMapping("updateEmployee")
    public String updateEmployee(@RequestBody EmployeePayload employeeRequest){
        Employee employee= employeeService.updateEmployee(employeeRequest);
        return "Update Success with " + employee.getCorporateEmail();
    }

    @DeleteMapping("deleteEmployee")
    public String DeleteEmployee(@RequestParam("mail") String mail){
        employeeService.deleteEmployee(mail);
        return "Employee deleted "+mail;
    }

}
