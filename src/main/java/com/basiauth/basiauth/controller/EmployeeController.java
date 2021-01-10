package com.basiauth.basiauth.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.basiauth.basiauth.entity.Employee;
import com.basiauth.basiauth.service.EmployeeServiceImpl;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    @Autowired
    private EmployeeServiceImpl employeeService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<Employee> getEmployees() {
        List<Employee> employeeList = employeeService.getAllEmployees();
        return employeeList;
    }

    @GetMapping(path="{employeeId}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_GENERAL')")
    public Employee getEmployeeById(@PathVariable("employeeId") Long id) {
    	Optional<Employee> employeeOption = employeeService.getEmployeeById(id);
    	Employee emp = employeeOption.get();
    	return emp;
    }

    //register
    @PostMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void postEmployee(@RequestBody Employee employee) {
    	employeeService.createEmployee(employee);
    }


    //update
    @PutMapping(path="{employeeId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void updateEmployee(@RequestBody Employee employee,@PathVariable("employeeId") Long id) {
        Optional<Employee> employee1 = employeeService.getEmployeeById(id);
        Employee emp = employee1.get();
        employeeService.updateEmployeeById(employee,id);
    }

    @DeleteMapping(path="{employeeId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteEmployee(@PathVariable("employeeId") Long id) {
    	employeeService.deleteEmployeeById(id);
    }
}
