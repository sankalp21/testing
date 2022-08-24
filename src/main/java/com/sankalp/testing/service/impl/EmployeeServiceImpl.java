package com.sankalp.testing.service.impl;

import com.sankalp.testing.exceptions.ResourceNotFoundException;
import com.sankalp.testing.model.Employee;
import com.sankalp.testing.repository.EmployeeRepository;
import com.sankalp.testing.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public void saveEmployee(Employee employee) {
        Optional<Employee> employeeOptional = employeeRepository.findByEmail(employee.getEmail());

        if(employeeOptional.isPresent()){
            throw new ResourceNotFoundException(String.format("Employee with email : %s already exists", employee.getEmail()));
        }

        employeeRepository.save(employee);
    }
}
