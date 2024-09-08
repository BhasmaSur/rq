package com.example.rqchallenge.employees.service;

import com.example.rqchallenge.employees.dto.Employee;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


public interface EmployeeService {
    ResponseEntity<List<Employee>> getAllEmployees();

    ResponseEntity<List<Employee>> getEmployeeByName(String searchString);

    ResponseEntity<Employee> getEmployeeById(String id);

    ResponseEntity<Integer> getHighestSalaryOfEmployees();

    ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames();

    ResponseEntity<Employee> createEmployee(Map<String, Object> employeeInput);

    ResponseEntity<String> deleteEmployeeById(String id);

}
