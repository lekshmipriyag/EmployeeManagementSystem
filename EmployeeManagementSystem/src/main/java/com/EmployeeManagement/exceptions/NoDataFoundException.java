package com.EmployeeManagement.exceptions;

public class NoDataFoundException extends RuntimeException {

    public NoDataFoundException() {
        super("No data found");
    }
}