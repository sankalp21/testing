package com.sankalp.testing.service;

import com.sankalp.testing.model.Employee;

import java.util.List;

public interface EmployeeService {
   
    public Employee saveEmployee(Employee employee);
    public List<Employee> getAllEmployee();
}
