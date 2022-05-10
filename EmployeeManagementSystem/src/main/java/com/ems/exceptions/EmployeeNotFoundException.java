package com.ems.exceptions;

public class EmployeeNotFoundException extends  RuntimeException{

    public EmployeeNotFoundException(Integer id) {

        super(String.format("Employee with Id %d not found", id));
    }
}
