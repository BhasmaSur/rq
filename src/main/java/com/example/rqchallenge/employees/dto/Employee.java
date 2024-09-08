package com.example.rqchallenge.employees.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Employee {
    private int id;
    private String employeeName;
    private int employeeSalary;
    private int employeeAge;
    private String profileImg;
}
