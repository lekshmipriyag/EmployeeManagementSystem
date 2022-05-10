package com.ems.controller;

import com.ems.model.Department;
import com.ems.model.Employee;
import com.ems.model.ResponseData;
import com.ems.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RegisterControllerTests {


    @Autowired
    RegisterController registerController;

    @Autowired
    DepartmentController departmentController;


    @Autowired
    EmployeeRepository employeeRepository;

    Department department = null;
    Employee employee = null;

    @BeforeEach
    public void init() {
        department = EmployeeData.addDepartment();
        employee = EmployeeData.addEmployee();
    }

    @Test
    @Order(1)
    public void testRegisterEmployee() {

        Employee emp = new Employee();
        Department dept = new Department();
        emp.setId(1);
        emp.setName("Alex");
        emp.setEmployeeNumber("EMP123");
        emp.setDob("17/03/1993");
        emp.setEmailID("alex@gmail.com");
        emp.setMobile("0431440140");
        emp.setQualification("MCA");
        emp.setExperience(3);
        emp.setJoiningDate("08/12/2021");
        emp.setCity("Melbourne");
        emp.setState("VIC");
        dept.setDEPARTMENTID(1);
        dept.setDepartmentname("Human Resources");
       // emp.setDepartment(dept);
       // departmentController.addDepartment(department);
        ResponseEntity<ResponseData> response = registerController.registerEmployee(emp);
      //  System.out.println(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    }

    @Test
    @Order(2)
    public void testGetEmployeeByIDCase1() {
        int id = 125;
        ResponseEntity<ResponseData> response = registerController.getEmployeeByID(id);
        assertEquals("No Data Found", response.getBody().getMessage());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


    @Test
    @Order(3)
    public void testGetAllEmployees() {
        ResponseEntity<ResponseData> response = registerController.getAllEmployees();
        if(employeeRepository.count() ==0){
            assertEquals ("No Data Found. Employee details are not added yet", response.getBody().getMessage());
            assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        }else{
            assertEquals ("Data Found", response.getBody().getMessage());
            assertEquals(HttpStatus.OK, response.getStatusCode());
        }

    }


    @Test
    @Order(4)
    public void testGetEmployeeByNameCase1() {
        String name = "XYZ237THY";
        ResponseEntity<ResponseData> response = registerController.getEmployeeByName(name);
        assertEquals("Searched Employee is not found", response.getBody().getMessage());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @Order(5)
    public void testGetEmployeeByNameCase2() {
        String name = "Sentil";
        ResponseEntity<ResponseData> response = registerController.getEmployeeByName(name);
        assertEquals("Searched Employee is not found", response.getBody().getMessage());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @Order(6)
    public void testGetEmployeeCount() {
        ResponseEntity<ResponseData> response = registerController.getEmployeeCount();
         assertEquals("Total No: of Staff: 0", response.getBody().getMessage());
    }

    @Test
    @Order(7)
    public void testDeleteEmployeeCase2() {
        int id = 177;
        ResponseEntity<ResponseData> response = registerController.deleteEmployee(id);
        assertEquals("The id is invalid for the employee.", response.getBody().getMessage());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @Order(8)
    public void testDeleteEmployeeListCase3() {
        List<Integer> idList = new ArrayList<>();
        idList.add(1);
        idList.add(2);
        idList.add(3);
        idList.add(4);
        idList.add(5);
        idList.add(6);
        idList.add(7);
        idList.add(8);
        idList.add(9);
        idList.add(10);
        ResponseEntity<ResponseData> response = registerController.deleteEmployeeList(idList);
        assertEquals("Failed. One of the employee IDs' is not in the DB", response.getBody().getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Order(9)
    public void testDeleteEmployeeListCase4() {
        List<Integer> idList = new ArrayList<>();
        ResponseEntity<ResponseData> response = registerController.deleteEmployeeList(idList);
        assertEquals("The List is empty", response.getBody().getMessage());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    @Order(10)
    public void testGetEmployeeByNumberCase1() {
        String empNumber = "EMP103";
        ResponseEntity<ResponseData> response = registerController.getEmployeeByNumber(empNumber);
        assertEquals("Searched Employee is not found", response.getBody().getMessage());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

}
