package com.basiauth.basiauth.service;

import com.basiauth.basiauth.entity.Employee;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    public List<Employee> getAllEmployees();
    public Optional<Employee> getEmployeeById(Long id);
    public void createEmployee(Employee employee);
    public void updateEmployeeById(Employee employee, Long id);
    public void deleteEmployeeById(Long id);
}
