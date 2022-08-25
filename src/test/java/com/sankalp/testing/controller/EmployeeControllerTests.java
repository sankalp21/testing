package com.sankalp.testing.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sankalp.testing.model.Employee;
import com.sankalp.testing.service.EmployeeService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import static org.mockito.BDDMockito.given;

import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest
public class EmployeeControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    //tells spring to mock employee service
    @MockBean
    private  EmployeeService employeeService;

    //Junit for creating employee
    @Test
    public void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee() throws Exception{
        //given -> precondition or setup
        Employee employee = Employee.builder()
                .firstName("rao")
                .lastName("try")
                .email("rao@mail.com")
                .build();

        given(employeeService.saveEmployee(ArgumentMatchers.any(Employee.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        //when -> action or behavior that we want to test
        ResultActions response = mockMvc.perform(
                MockMvcRequestBuilders
                .post("/api/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(employee))
        );

        //then -> verify results
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName",
                        CoreMatchers.is(employee.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName",
                        CoreMatchers.is(employee.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email",
                        CoreMatchers.is(employee.getEmail())));

    }

    //Junit for getting all employee
    @Test
    public void givenListOfEmployee_whenGetAllEmployee_thenReturnEmployeeList() throws Exception{
        //given -> precondition or setup
        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(Employee.builder().firstName("ram").lastName("babu").email("ram@mail.com").build());
        employeeList.add(Employee.builder().firstName("shyam").lastName("babu").email("shyam@mail.com").build());

        given(employeeService.getAllEmployee()).willReturn(employeeList);

        //when -> action or behavior we want to test
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/employee"));

        //then -> verify results
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()",
                        CoreMatchers.is(employeeList.size())));
    }

}
