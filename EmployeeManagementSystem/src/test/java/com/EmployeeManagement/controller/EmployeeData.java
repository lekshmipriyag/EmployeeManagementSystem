package com.EmployeeManagement.controller;

import com.EmployeeManagement.model.Department;
import com.EmployeeManagement.model.Employee;

public class EmployeeData {

    public  static Department addDepartment() {
        Department department = new Department(1,"Human Resources");
        return department;
    }

    public static Employee addEmployee() {
        Employee employee = new Employee(1, "EMP123", "Jhon", "17/03/1993",
                "Male", "jhon@gmail.com", "0431440140", "MCA", 3,
                "23/12/2020", "Melbourne", "Victoria", new Department(1,"Human Resources"));
        return employee;
    }
}
