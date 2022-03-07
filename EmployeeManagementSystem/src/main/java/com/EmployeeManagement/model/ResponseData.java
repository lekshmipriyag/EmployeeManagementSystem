package com.EmployeeManagement.model;

import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class ResponseData {
    private String message;
    private List<Employee> employeeList;
    private List<Department> departmentList;
    private List<String> employeeDataBasedOnDepartment;
    private String test;
}

