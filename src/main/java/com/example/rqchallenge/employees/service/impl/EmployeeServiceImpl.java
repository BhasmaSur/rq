package com.example.rqchallenge.employees.service.impl;

import com.example.rqchallenge.employees.dto.Employee;
import com.example.rqchallenge.employees.employeeutility.EmployeeUtil;
import com.example.rqchallenge.employees.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {


    @Autowired
    EmployeeUtil employeeUtil;

    @Override
    public ResponseEntity<List<Employee>> getAllEmployees() {
        log.info("Inside :: Service :: getAllEmployees");
        List<Employee> employeeList = employeeUtil.getAllEmployee();
        if (null != employeeList && employeeList.size() > 0) {
            return ResponseEntity.ok(employeeList);
        }
        log.error("Failed to fetch employees from api");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @Override
    public ResponseEntity<List<Employee>> getEmployeeByName(String searchString) {
        log.info("Inside :: Service :: getEmployeeByName");
        List<Employee> employeeList = employeeUtil.getAllEmployee();
        if (null != employeeList && employeeList.size() > 0) {
            List<Employee> employees = employeeList.stream().filter(emp -> emp.getEmployeeName().equals(searchString)).collect(Collectors.toList());
            if(employees.size() > 0){
                return ResponseEntity.ok(employees);
            }
            ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        log.error("Failed to fetch employees by name from api");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @Override
    public ResponseEntity<Employee> getEmployeeById(String id) {
        log.info("Inside :: Service :: getEmployeeById");
        Employee employee = employeeUtil.getEmployeeById(id);
        if (null != employee) {
            return ResponseEntity.ok(employee);
        }
        log.error("Failed to fetch employees by id from api");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @Override
    public ResponseEntity<Integer> getHighestSalaryOfEmployees() {
        log.info("Inside :: Service :: getHighestSalaryOfEmployees");
        List<Employee> employeeList = employeeUtil.getAllEmployee();
        if (null != employeeList) {
            if(employeeList.size() > 0){
                IntSummaryStatistics summaryStatistics = employeeList.stream().map(Employee::getEmployeeSalary).mapToInt(v->v).summaryStatistics();
                return ResponseEntity.ok(summaryStatistics.getMax());
            }
            log.error("Employee list is empty");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        log.error("Failed to fetch employees from api");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @Override
    public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() {
        log.info("Inside :: Service :: getTopTenHighestEarningEmployeeNames");
        List<Employee> employeeList = employeeUtil.getAllEmployee();
        if (null != employeeList && employeeList.size() > 0) {
            List<String> sortedEmployeeList = employeeList.stream().sorted(Comparator.comparing(Employee::getEmployeeSalary).reversed()).limit(10).map(emp -> emp.getEmployeeName()).collect(Collectors.toList());
            return ResponseEntity.ok(sortedEmployeeList);
        }
        log.error("Failed to fetch employees by id from api");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @Override
    public ResponseEntity<Employee> createEmployee(Map<String, Object> employeeInput) {
        log.info("Inside :: Service :: createEmployee");
        Map<String, Object> employeeMap = employeeUtil.addEmployee(employeeInput);
        if(null != employeeMap){
            return ResponseEntity.ok(employeeUtil.copyPropertiesToEmployee(employeeMap));
        }
        log.error("Failed to create employee using api");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @Override
    public ResponseEntity<String> deleteEmployeeById(String id) {
        log.info("Inside :: Service :: deleteEmployeeById");
        String message = employeeUtil.deleteEmployeeById(id);
        if(null != message){
            return ResponseEntity.ok(message);
        }
        log.error("Failed to delete employee using api");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
