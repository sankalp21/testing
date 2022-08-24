package com.sankalp.testing.service;

import com.sankalp.testing.exceptions.ResourceNotFoundException;
import com.sankalp.testing.model.Employee;
import com.sankalp.testing.repository.EmployeeRepository;
import com.sankalp.testing.service.impl.EmployeeServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;

    @BeforeEach
    public void setup(){
        employee = Employee.builder()
                .firstName("Ram")
                .lastName("Singh")
                .email("ram.singh@mail.com")
                .build();
    }

    //Junit for saveEmoloyee
    @Test
    public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployee(){
        //given -> precondition or setup
        given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.empty());
        given(employeeRepository.save(employee)).willReturn(employee);

        //when ->  save employee
        Employee savedEmployee = employeeService.saveEmployee(employee);

        //then -> verify results
        Assertions.assertThat(savedEmployee).isNotNull();

    }

    //Jnuit for saveEmployee where exception is thrown
    @Test
    public void givenExistingEmployeeObject_whenSaveEmployee_thenThrowException(){
        //given -> precondition or setup
        given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.of(employee));

        //when ->  save employee
        org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class, ()->{
            employeeService.saveEmployee(employee);
        });


        //then -> verify results
        verify(employeeRepository, never()).save(any(Employee.class));
    }
}
