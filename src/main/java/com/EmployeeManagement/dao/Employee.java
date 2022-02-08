package com.EmployeeManagement.dao;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity
@NoArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private int id;

    @NotBlank(message = "Name is required")
    //@Pattern(regexp = "[A-Z][a-z]+$", message = "Invalid Name")
    @Getter
    @Setter
    private String name;

    @NotBlank(message = "Date Of Birth is required")
    @Pattern(regexp = "[0-3][0-9]/[0-1][1-9]/[0-9][0-9][0-9][0-9]", message = "Invalid Date Of Birth")
    @Getter
    @Setter
    private String dob;

    @Getter
    @Setter
    private String qualification;

    @Getter
    @Setter
    private int experience;

    @Getter
    @Setter
    @NotBlank(message = "Joining Date is required")
    @Pattern(regexp = "[0-3][0-9]/[0-1][1-9]/[0-9][0-9][0-9][0-9]", message = "Invalid Joining Date")
    private String joiningDate;


    @Getter
    @Setter
    @NotBlank(message = "Email ID is required")
    @Email(message = "Invalid Email")
    private String emailID;

}
