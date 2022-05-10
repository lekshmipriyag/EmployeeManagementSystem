package com.ems.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "Employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Pattern(regexp="^[a-zA-Z0-9]{6}",message="Employee Number length must be 6")
    @NotBlank(message = "Employee Number is required")
    @Column(name = "employee_number")
    private String employeeNumber;

    @Pattern(regexp="^[a-zA-Z ]*$",message="Invalid Name")
    @NotBlank(message = "Name is required")
    @Column(name = "name")
    private String name;


    @NotBlank(message = "Date Of Birth is required")
    @Pattern(regexp = "[0-3][0-9]/[0-1][1-9]/[0-9][0-9][0-9][0-9]", message = "Invalid Date Of Birth")
    @Column(name = "dob")
    private String dob;

    @NotBlank(message = "Gender is required")
    @Column(name = "gender")
    private String gender;

    @NotBlank(message = "Email ID is required")
    @Email(message = "Invalid Email")
    private String emailID;


    //@Pattern(regexp = "^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{4}$", message = "Incorrect Number Format")
    @Pattern(regexp="(^$|[0-9]{10})", message = "Invalid Phone Number")
    @Column(name = "mobile")
    private String mobile;

    @Column(name = "qualification")
    private String qualification;

    @Column(name = "experience")
    private int experience;

    @NotBlank(message = "Joining Date is required")
    @Pattern(regexp = "[0-3][0-9]/[0-1][1-9]/[0-9][0-9][0-9][0-9]", message = "Invalid Date Of Birth")
    @Column(name = "joining_date")
    private String joiningDate;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

   //@ManyToOne(cascade = { CascadeType.PERSIST })
    @ManyToOne
    @JoinColumn(name = "DEPARTMENTID")
    private Department department;

}