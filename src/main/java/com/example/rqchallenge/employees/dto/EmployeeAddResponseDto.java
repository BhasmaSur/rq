package com.example.rqchallenge.employees.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmployeeAddResponseDto {
    private String status;
    private Map<String, Object> data;
    private String message;
}
