package com.sankalp.testing.service;

import com.sankalp.testing.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
   
    public Employee saveEmployee(Employee employee);
    public List<Employee> getAllEmployee();
    public Optional<Employee> getEmployeeById(long id);
    public Employee updateEmployee(Employee employee);
}
