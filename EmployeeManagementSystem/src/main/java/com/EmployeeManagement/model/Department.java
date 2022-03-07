package com.EmployeeManagement.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Data
@AllArgsConstructor
@Table(name = "Department")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DEPARTMENTID")
    private int DEPARTMENTID;

    @Column(name = "departmentname")
    private String departmentname;

    public void setDEPARTMENTID(int DEPARTMENTID) {
        this.DEPARTMENTID = DEPARTMENTID;
    }

    public void setDepartmentname(String departmentname) {
        this.departmentname = departmentname;
    }
}
