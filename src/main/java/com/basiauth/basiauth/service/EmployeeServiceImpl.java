package com.basiauth.basiauth.service;

import com.basiauth.basiauth.entity.Employee;
import com.basiauth.basiauth.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public List<Employee> getAllEmployees() {
        Iterable<Employee> employeeIterable = employeeRepository.findAll();
        List<Employee> employeeList = new ArrayList<>();
        employeeIterable.forEach(employeeList::add);

        return employeeList;
    }

    @Override
    public Optional<Employee> getEmployeeById(Long id) {
       Optional<Employee> employee = employeeRepository.findById(id);
       return employee;
    }

    @Override
    public void createEmployee(Employee employee) {
        Employee employee1 = new Employee();
        employee1 = employeeRepository.save(employee);
    }

    @Override
    public void updateEmployeeById(Employee employee, Long id) {
        Optional<Employee> employee1 = employeeRepository.findById(id);
       Employee emp = employee1.get();
       emp =  employeeRepository.save(employee);
       System.out.println(emp);
    }

    @Override
    public void deleteEmployeeById(Long id) {
        employeeRepository.deleteById(id);
    }
}
