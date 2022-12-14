package com.sankalp.testing.service;

import com.sankalp.testing.exceptions.ResourceNotFoundException;
import com.sankalp.testing.model.Employee;
import com.sankalp.testing.repository.EmployeeRepository;
import com.sankalp.testing.service.impl.EmployeeServiceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

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

        //when ->  behavior that we need to test
        Employee savedEmployee = employeeService.saveEmployee(employee);

        //then -> verify results
        assertThat(savedEmployee).isNotNull();

    }

    //Jnuit for saveEmployee where exception is thrown
    @Test
    public void givenExistingEmployeeObject_whenSaveEmployee_thenThrowException(){
        //given -> precondition or setup
        given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.of(employee));

        //when ->  behavior that we need to test
        org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class, ()->{
            employeeService.saveEmployee(employee);
        });


        //then -> verify results
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    //Junit for getting all employee
    @Test
    public void givenEmployeeList_whenGetAllEmployee_thenReturnEmployeeList(){
        //given -> precondition or setup
        Employee employee1 = Employee.builder()
                .firstName("Joe")
                .lastName("ro")
                .email("joe.ro@mail.com")
                .build();

        List<Employee> returnResult = new ArrayList<>();

        returnResult.add(employee);
        returnResult.add(employee1);

        given(employeeRepository.findAll()).willReturn(returnResult);

        //when ->  behavior that we need to test
        List<Employee> employeeList = employeeService.getAllEmployee();

        //then -> verify results
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);
    }

    //Junit for getting all employee negative scenario
    @Test
    public void givenEmptyEmployeeList_whenGetAllEmployee_thenReturnEmptyEmployeeList(){
        //given -> precondition or setup
        given(employeeRepository.findAll()).willReturn(Collections.emptyList());

        //when ->  behavior that we need to test
        List<Employee> employeeList = employeeService.getAllEmployee();

        //then -> verify results
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(0);
    }

    //Junit for getting employee by id
    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployee(){
        //given -> precondition or setup
        given(employeeRepository.findById(employee.getId())).willReturn(Optional.of(employee));

        //when ->  behavior that we need to test
       Employee employeeById = employeeService.getEmployeeById(employee.getId()).get();

        //then -> verify results
        assertThat(employeeById).isNotNull();
    }

    //Junit for updating employee
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee(){
        //given -> precondition or setup
        given(employeeRepository.save(employee)).willReturn(employee);

        employee.setEmail("shyam.sunder@mail.com");
        employee.setFirstName("shyam");
        employee.setLastName("sunder");

        //when -> behavior that we need to test
        Employee updatedEmployee = employeeService.updateEmployee(employee);

        //then -> verify results
        assertThat(updatedEmployee.getEmail()).isEqualTo("shyam.sunder@mail.com");
        assertThat(updatedEmployee.getFirstName()).isEqualTo("shyam");
        assertThat(updatedEmployee.getLastName()).isEqualTo("sunder");
    }

    //Junit for deleting employee by id
    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenReturnNothing(){
        //given -> precondition or setup
        willDoNothing().given(employeeRepository).deleteById(employee.getId());

        //when -> behavior that we need to test
        employeeService.deleteEmployee(employee.getId());

        //then -> verify results
        verify(employeeRepository, times(1)).deleteById(employee.getId());
    }
}
