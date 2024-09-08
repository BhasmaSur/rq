package com.example.rqchallenge.employees.controllerimpl;

import com.example.rqchallenge.employees.IEmployeeController;
import com.example.rqchallenge.employees.constants.EmployeeConstants;
import com.example.rqchallenge.employees.dto.Employee;
import com.example.rqchallenge.employees.service.EmployeeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(IEmployeeController.class)
class IEmployeeControllerImplTest {

    @MockBean
    EmployeeService employeeService;

    @Autowired
    private MockMvc mockMvc;

    private Employee employee1, employee2, employee3;
    private List<Employee> employeeList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        employee1 = new Employee(1, "Manpreet Singh", 78666, 45, null);
        employee2 = new Employee(2, "Gurmeet Lala", 45636, 34, null);
        employee3 = new Employee(3, "Gurmeet Lala", 90454, 23, null);
        employeeList.add(employee1);
        employeeList.add(employee2);
        employeeList.add(employee3);
    }

    @AfterEach
    void tearDown() throws Exception {
    }

    @Test
    void testGetAllEmployees() throws Exception {
        when(employeeService.getAllEmployees()).thenReturn(ResponseEntity.ok(employeeList));
        this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void testGetEmployeesByNameSearch() throws Exception {
        String employeeToSaerch = "Gurmeet Lala";
        List<Employee> employees = employeeList.stream().filter(emp -> emp.getEmployeeName().equals(employeeToSaerch)).collect(Collectors.toList());
        when(employeeService.getEmployeeByName(employeeToSaerch)).thenReturn(ResponseEntity.ok(employees));
        this.mockMvc.perform(get("/search/" + employeeToSaerch)).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void testGetEmployeeById() throws Exception {
        String employeeId = "2";
        when(employeeService.getEmployeeById(employeeId)).thenReturn(ResponseEntity.ok(employeeList.get(0)));
        this.mockMvc.perform(get("/" + employeeId)).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void testGetHighestSalaryOfEmployees() throws Exception {
        when(employeeService.getHighestSalaryOfEmployees()).thenReturn(ResponseEntity.ok(employeeList.get(2).getEmployeeSalary()));
        this.mockMvc.perform(get("/highestSalary")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void testGetTopTenHighestEarningEmployeeNames() throws Exception {
        List<String> highestEarningEmployeeNames = Arrays.asList("Gurmeet Lala", "Manpreet Singh", "Gurmeet Lala");
        when(employeeService.getTopTenHighestEarningEmployeeNames()).thenReturn(ResponseEntity.ok(highestEarningEmployeeNames));
        this.mockMvc.perform(get("/topTenHighestEarningEmployeeNames")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void testCreateEmployee() throws Exception {
        Map<String, Object> employeeToAdd = new HashMap<>();
        employeeToAdd.put(EmployeeConstants.EMPLOYEE_NAME, employee1.getEmployeeName());
        employeeToAdd.put(EmployeeConstants.EMPLOYEE_SALARY, employee1.getEmployeeSalary());
        employeeToAdd.put(EmployeeConstants.EMPLOYEE_AGE, employee1.getEmployeeAge());
        when(employeeService.createEmployee(employeeToAdd)).thenReturn(ResponseEntity.ok(employee1));
        this.mockMvc.perform(get("/createEmployee")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    void deleteEmployeeById() throws Exception {
        when(employeeService.deleteEmployeeById(String.valueOf(employee2.getId()))).thenReturn(ResponseEntity.ok(EmployeeConstants.EMPLOYEE_DELETE_MESSAGE));
        this.mockMvc.perform(get("/createEmployee")).andDo(print()).andExpect(status().isOk());
    }
}