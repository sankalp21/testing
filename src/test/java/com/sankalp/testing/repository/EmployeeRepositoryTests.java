package com.sankalp.testing.repository;

import com.sankalp.testing.model.Employee;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class EmployeeRepositoryTests {

    @Autowired
    private EmployeeRepository employeeRepository;

    //Junit test for saving employee
    @Test
    public void givenEmployeeObject_whenSave_thenReturnSavedEmployee(){
        //given -> precondition or setup
        Employee sankalp = Employee.builder()
                .firstName("sankalp")
                .lastName("sinha")
                .email("ff@mail.com")
                .build();

        //when -> action or behavior that we need to test
        Employee savedEmployee = employeeRepository.save(sankalp);

        //then -> verify the output
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);
    }


    //Junit test for getting all employees
    @Test
    public void givenEmployeeLists_whenfindAll_thenReturnEmployeeLists(){
        //given -> precondition  or setup
        Employee ram = Employee.builder()
                .firstName("Ram")
                .lastName("Singh")
                .email("ram.singh@mail.com")
                .build();

        Employee shyam = Employee.builder()
                .firstName("Shyam")
                .lastName("Sunder")
                .email("Shyam.sunder@mail.com")
                .build();

        employeeRepository.save(ram);
        employeeRepository.save(shyam);

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
        Employee ram = Employee.builder()
                .firstName("Ram")
                .lastName("Singh")
                .email("ram.singh@mail.com")
                .build();

        employeeRepository.save(ram);

        //when -> action or behvior we want to test
        Employee employee = employeeRepository.findById(ram.getId()).get();

        //then -> verify results
        assertThat(employee).isNotNull();
    }

    //Junit for getting employee by email
    @Test
    public void givenEmployeeObject_whenFindByEmail_thenReturnEmployee(){
        //given -> precondition or setup
        Employee ram = Employee.builder()
                .firstName("ram")
                .lastName("singh")
                .email("ram.singh@mail.com")
                .build();

        employeeRepository.save(ram);

        //when -> action or behavior we want to test
        Employee employee = employeeRepository.findByEmail(ram.getEmail()).get();

        //then -> verify results
        assertThat(employee).isNotNull();
    }

    //Junit for updating employee
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee(){
        //given -> precondition or setup
        Employee ram = Employee.builder()
                .firstName("ram")
                .lastName("singh")
                .email("ram.singh@mail.com")
                .build();

        employeeRepository.save(ram);

        //when -> action or behavior we want to test
        Employee savedEmployee = employeeRepository.findByEmail(ram.getEmail()).get();
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
        Employee ram = Employee.builder()
                .firstName("ram")
                .lastName("singh")
                .email("ram.singh@mail.com")
                .build();

        employeeRepository.save(ram);

        //when -> action or behavior we want to test
        employeeRepository.delete(ram);
        Optional<Employee> employeeOptional = employeeRepository.findById(ram.getId());

        //then -> verify results
        assertThat(employeeOptional).isEmpty();
    }

    //Junit for finding Employee by first and last name employee
    @Test
    public void givenEmployeeObject_whenFindByInitials_thenReturnEmployee(){
        //given -> precondition or setup
        String firstName = "ram";
        String lastName = "singh";

        Employee ram = Employee.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email("ram.singh@mail.com")
                .build();

        employeeRepository.save(ram);

        //when -> action or behavior we want to test
        Optional<Employee> employeeOptional = employeeRepository.findByInitials(firstName, lastName);

        //then -> verify results
        assertThat(employeeOptional).isNotEmpty();
    }
}
