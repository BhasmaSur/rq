package com.example.rqchallenge.employees.service.impl;

import com.example.rqchallenge.employees.constants.EmployeeConstants;
import com.example.rqchallenge.employees.dto.Employee;
import com.example.rqchallenge.employees.employeeutility.EmployeeUtil;
import com.example.rqchallenge.employees.service.EmployeeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;


@SpringBootTest
class EmployeeServiceImplTest {

    @MockBean
    private EmployeeUtil employeeUtil;
    @Autowired
    private EmployeeService employeeService;
    private Employee employee;
    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        employee = new Employee(1, "Manpreet Singh", 78666, 45, null);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void testGetAllEmployees() {
        when(employeeUtil.getAllEmployee()).thenReturn(new ArrayList<Employee>(Collections.singleton(employee)));
        assertThat(employeeService.getAllEmployees().getBody().get(0).getId()).isEqualTo(employee.getId());
    }

    @Test
    void testGetEmployeeByName() {
        when(employeeUtil.getAllEmployee()).thenReturn(new ArrayList<Employee>(Collections.singleton(employee)));
        assertThat(employeeService.getEmployeeByName(employee.getEmployeeName()).getBody().get(0).getEmployeeName()).isEqualTo(employee.getEmployeeName());
    }

    @Test
    void testGetEmployeeById() {
        when(employeeUtil.getEmployeeById(String.valueOf(employee.getId()))).thenReturn(employee);
        assertThat(employeeService.getEmployeeById(String.valueOf(employee.getId())).getBody().getId()).isEqualTo(employee.getId());
    }

    @Test
    void testGetHighestSalaryOfEmployees() {
        when(employeeUtil.getAllEmployee()).thenReturn(new ArrayList<Employee>(Collections.singleton(employee)));
        assertThat(employeeService.getHighestSalaryOfEmployees().getBody()).isEqualTo(employee.getEmployeeSalary());
    }

    @Test
    void testGetTopTenHighestEarningEmployeeNames() {
        when(employeeUtil.getAllEmployee()).thenReturn(new ArrayList<Employee>(Collections.singleton(employee)));
        assertThat(employeeService.getTopTenHighestEarningEmployeeNames().getBody().get(0)).isEqualTo(employee.getEmployeeName());
    }

    @Test
    void testCreateEmployee() {
        Map<String, Object> employeeToAdd = new HashMap<>();
        employeeToAdd.put(EmployeeConstants.EMPLOYEE_NAME, employee.getEmployeeName());
        employeeToAdd.put(EmployeeConstants.EMPLOYEE_SALARY, employee.getEmployeeSalary());
        employeeToAdd.put(EmployeeConstants.EMPLOYEE_AGE, employee.getEmployeeAge());
        employeeToAdd.put(EmployeeConstants.EMPLOYEE_ID, employee.getId());
        when(employeeUtil.addEmployee(employeeToAdd)).thenReturn(employeeToAdd);
        when(employeeUtil.copyPropertiesToEmployee(employeeToAdd)).thenReturn(employee);
        assertThat(employeeService.createEmployee(employeeToAdd).getBody().getEmployeeName()).isEqualTo(employee.getEmployeeName());
    }

    @Test
    void deleteEmployeeById() {
        when(employeeUtil.deleteEmployeeById(String.valueOf(employee.getId()))).thenReturn(EmployeeConstants.EMPLOYEE_DELETE_MESSAGE);
        assertThat(employeeService.deleteEmployeeById(String.valueOf(employee.getId())).getBody()).isEqualTo(EmployeeConstants.EMPLOYEE_DELETE_MESSAGE);
    }
}