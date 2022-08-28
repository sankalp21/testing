package com.sankalp.testing.repository;

import com.sankalp.testing.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EmployeeRepositoryIT {

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;

    @BeforeEach
    public void setup(){
        employeeRepository.deleteAll();
        employee = Employee.builder()
                .firstName("Ram")
                .lastName("Singh")
                .email("ram.singh@mail.com")
                .build();
    }

    //Junit test for saving employee
    @Test
    public void givenEmployeeObject_whenSave_thenReturnSavedEmployee(){
        //given -> precondition or setup

        //when -> action or behavior that we need to test
        Employee savedEmployee = employeeRepository.save(employee);

        //then -> verify the output
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);
    }


    //Junit test for getting all employees
    @Test
    public void givenEmployeeLists_whenfindAll_thenReturnEmployeeLists(){
        //given -> precondition  or setup

        Employee employee1 = Employee.builder()
                .firstName("Shyam")
                .lastName("Sunder")
                .email("Shyam.sunder@mail.com")
                .build();

        employeeRepository.save(employee);
        employeeRepository.save(employee1);

        //when -> action or behavior that we need to test
        List<Employee> employeeLists = employeeRepository.findAll();

        //then -> verify the output
        assertThat(employeeLists).isNotNull();
        assertThat(employeeLists.size()).isEqualTo(2);
    }

    //Junit for getting employee by id
    @Test
    public void givenEmployeeObject_whenFinfById_thenReturnEmployee(){
        //given -> precondition or setup

        employeeRepository.save(employee);

        //when -> action or behvior we want to test
        Employee fetchedEmployee = employeeRepository.findById(employee.getId()).get();

        //then -> verify results
        assertThat(fetchedEmployee).isNotNull();
    }

    //Junit for getting employee by email
    @Test
    public void givenEmployeeObject_whenFindByEmail_thenReturnEmployee(){
        //given -> precondition or setup
        employeeRepository.save(employee);

        //when -> action or behavior we want to test
        Employee fetchedEmployee = employeeRepository.findByEmail(employee.getEmail()).get();

        //then -> verify results
        assertThat(fetchedEmployee).isNotNull();
    }

    //Junit for updating employee
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee(){
        //given -> precondition or setup
        employeeRepository.save(employee);

        //when -> action or behavior we want to test
        Employee savedEmployee = employeeRepository.findByEmail(employee.getEmail()).get();
        savedEmployee.setLastName("cena");
        savedEmployee.setEmail("ram.cena@mail.com");

        Employee updatedEmployee = employeeRepository.save(savedEmployee);

        //then -> verify results
        assertThat(updatedEmployee.getLastName()).isEqualTo("cena");
        assertThat(updatedEmployee.getEmail()).isEqualTo("ram.cena@mail.com");
    }

    //Junit for deleting employee
    @Test
    public void givenEmployeeObject_whenDelete_thenRemoveEmployee(){
        //given -> precondition or setup
        employeeRepository.save(employee);

        //when -> action or behavior we want to test
        employeeRepository.delete(employee);
        Optional<Employee> employeeOptional = employeeRepository.findById(employee.getId());

        //then -> verify results
        assertThat(employeeOptional).isEmpty();
    }

    //Junit for finding Employee by first and last name employee using index params
    @Test
    public void givenEmployeeObject_whenFindByInitialsViaIndexParams_thenReturnEmployee(){
        //given -> precondition or setup
        employeeRepository.save(employee);

        //when -> action or behavior we want to test
        Optional<Employee> employeeOptional = employeeRepository.findByInitials(employee.getFirstName(), employee.getLastName());

        //then -> verify results
        assertThat(employeeOptional).isNotEmpty();
    }

    //Junit for finding Employee by first and last name employee using named params
    @Test
    public void givenEmployeeObject_whenFindByInitialsViaNamedParams_thenReturnEmployee(){
        //given -> precondition or setup
        employeeRepository.save(employee);

        //when -> action or behavior we want to test
        Optional<Employee> employeeOptional = employeeRepository.findByInitialsViaNamedParams(employee.getFirstName(), employee.getLastName());

        //then -> verify results
        assertThat(employeeOptional).isNotEmpty();
    }

    //Junit for finding Employee by first and last name employee using native index params
    @Test
    public void givenEmployeeObject_whenFindByInitialsViaNative_thenReturnEmployee(){
        //given -> precondition or setup
        employeeRepository.save(employee);

        //when -> action or behavior we want to test
        Optional<Employee> employeeOptional = employeeRepository.findByInitialViaNative(employee.getFirstName(), employee.getLastName());

        //then -> verify results
        assertThat(employeeOptional).isNotEmpty();
    }

    //Junit for finding Employee by first and last name employee using native named params
    @Test
    public void givenEmployeeObject_whenFindByInitialsViaNativeNamedParams_thenReturnEmployee(){
        //given -> precondition or setup
        employeeRepository.save(employee);

        //when -> action or behavior we want to test
        Optional<Employee> employeeOptional = employeeRepository.findByInitialsViaNativeNamedParams(employee.getFirstName(), employee.getLastName());

        //then -> verify results
        assertThat(employeeOptional).isNotEmpty();
    }
}
