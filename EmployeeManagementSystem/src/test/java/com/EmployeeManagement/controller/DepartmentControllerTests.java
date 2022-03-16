package com.EmployeeManagement.controller;

import com.EmployeeManagement.model.Department;
import com.EmployeeManagement.model.ResponseData;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DepartmentControllerTests {

    @Autowired
    DepartmentController departmentController;

    Department department = new Department();

    @BeforeEach
    public void init() {
        department = EmployeeData.addDepartment();
    }

    @Test
    @Order(1)
    public void testAddDepartment(){

        List<Department> departmentList = new ArrayList<>();
        ResponseEntity<ResponseData> response = departmentController.addDepartment(department);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Department added successfully.", response.getBody().getMessage());
    }

    @Test
    @Order(2)
    public void testgetAllDepartment() {

        ResponseEntity<ResponseData> response = departmentController.getAllDepartment();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Data Found", response.getBody().getMessage());
    }

    @Test
    @Order(3)
    public void testgetDepartmentByNameCase1() {
        String departmentName = "Finance";
        ResponseEntity<ResponseData> response = departmentController.getDepartmentByName(departmentName);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Searched Department is found", response.getBody().getMessage());
    }

    @Test
    @Order(4)
    public void testgetDepartmentByNameCase2() {
        String departmentName = "Java4g6";
        ResponseEntity<ResponseData> response = departmentController.getDepartmentByName(departmentName);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Failed", response.getBody().getMessage());
    }

    @Test
    @Order(5)
    public void testUpdateDepartmentDetailsCase1() {
        Department department = new Department();
        int id = 4;
        ResponseEntity<ResponseData> response = departmentController.updateDepartmentDetails(id, department);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("Searched Department ID is not equal with the Id you tried to update.", response.getBody().getMessage());
    }

    @Test
    @Order(6)
    public void testUpdateDepartmentDetailsCase2() {
        Department department = new Department();
        int id = 4;
        department.setDEPARTMENTID(id);
        department.setDepartmentname("Front-End Development");
        ResponseEntity<ResponseData> response = departmentController.updateDepartmentDetails(id, department);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("Department name exists.", response.getBody().getMessage());
    }

    @Test
    @Order(7)
    public void testUpdateDepartmentDetailsCase3() {
        Department department = new Department();
        int id = 11000;
        ResponseEntity<ResponseData> response = departmentController.updateDepartmentDetails(id, department);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Searched Department ID is not found.", response.getBody().getMessage());
    }


    @Test
    @Order(8)
    public void testDeleteDepartmentCase2() {
        int deptID = 5;
        ResponseEntity<ResponseData> response = departmentController.deleteDepartment(deptID);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Department Id 5 has been deleted", response.getBody().getMessage());
    }

    @Test
    @Order(8)
    public void testDeleteDepartmentCase3() {
        int deptID = 155555;
        ResponseEntity<ResponseData> response = departmentController.deleteDepartment(deptID);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Failed", response.getBody().getMessage());
    }

}
