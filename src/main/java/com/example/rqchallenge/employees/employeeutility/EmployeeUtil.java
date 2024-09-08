package com.example.rqchallenge.employees.employeeutility;

import com.example.rqchallenge.employees.constants.EmployeeConstants;
import com.example.rqchallenge.employees.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class EmployeeUtil {
    @Autowired
    RestTemplate restTemplate;

    public List<Employee> getAllEmployee() {
        log.info("Inside :: Employeeutil :: getAllEmployee");
        try {
            ResponseEntity<EmployeeResponseDto> employeeResponseEntity = restTemplate.getForEntity(
                    EmployeeConstants.EMPLOYEE_URL,
                    EmployeeResponseDto.class
            );
            if (null != employeeResponseEntity.getBody()) {
                log.info("Employees Fetched Successfullty");
                return employeeResponseEntity.getBody().getData();
            }
        } catch (Exception e) {
            log.error("Error in fetching employees", e);
        }
        return null;
    }

    public Employee getEmployeeById(String id) {
        log.info("Inside :: Employeeutil :: getEmployeeById");
        try {
            String getEmployeeUrl = String.format(EmployeeConstants.EMPLOYEE_GET_ID_URL, id);
            ResponseEntity<EmployeeGetResponseDto> employeeResponseEntity = restTemplate.getForEntity(
                    getEmployeeUrl,
                    EmployeeGetResponseDto.class
            );
            if (null != employeeResponseEntity.getBody()) {
                log.info("Employees Fetched Successfullty");
                return employeeResponseEntity.getBody().getData();
            }
        } catch (Exception e) {
            log.error("Error in fetching employees", e);
        }
        return null;
    }

    public Map<String, Object> addEmployee(Map<String, Object> employeeInput) {
        log.info("Inside :: Employeeutil :: addEmployee");
        try {
            String addUrl = String.format(EmployeeConstants.EMPLOYEE_CREATE_URL, employeeInput.get(EmployeeConstants.EMPLOYEE_NAME),
                    employeeInput.get(EmployeeConstants.EMPLOYEE_SALARY),
                    employeeInput.get(EmployeeConstants.EMPLOYEE_AGE));
            ResponseEntity<EmployeeAddResponseDto> employeeResponseEntity = restTemplate.postForEntity(
                    addUrl,
                    null,
                    EmployeeAddResponseDto.class
            );

            if (null != employeeResponseEntity.getBody()) {
                log.info("Employee Added Successfullty");
                return employeeResponseEntity.getBody().getData();
            }
        } catch (Exception e) {
            log.error("Error in adding employee", e);
        }
        return null;
    }

    public String deleteEmployeeById(String id) {
        log.info("Inside :: Employeeutil :: deleteEmployeeById");
        try {
            String deleteUrl = String.format(EmployeeConstants.EMPLOYEE_DELETE_URL, id);
            ResponseEntity<EmployeeDeleteResponseDto> employeeResponseEntity = restTemplate.exchange(
                    deleteUrl,
                    HttpMethod.DELETE,
                    null,
                    EmployeeDeleteResponseDto.class
            );

            if (null != employeeResponseEntity.getBody()) {
                log.info("Employee Deleted Successfullty");
                return employeeResponseEntity.getBody().getMessage();
            }
        } catch (Exception e) {
            log.error("Error in deleting employee", e);
        }
        return null;
    }

    public Employee copyPropertiesToEmployee(Map<String, Object> employeeMap) {
        log.info("Inside :: Employeeutil :: copyPropertiesToEmployee");
        return new Employee(
                (Integer) employeeMap.get(EmployeeConstants.EMPLOYEE_ID),
                (String) employeeMap.get(EmployeeConstants.EMPLOYEE_NAME),
                Integer.parseInt((String) employeeMap.get(EmployeeConstants.EMPLOYEE_SALARY)),
                Integer.parseInt((String) employeeMap.get(EmployeeConstants.EMPLOYEE_AGE)),
                null
        );
    }
}
