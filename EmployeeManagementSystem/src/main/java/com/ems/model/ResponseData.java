package com.ems.model;

import lombok.Data;

import java.util.List;

@Data
public class ResponseData {
    private String message;
    private List<Employee> employeeList;
    private List<Department> departmentList;
    private List<String> employeeDataBasedOnDepartment;
    private String test;
}

