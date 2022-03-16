package com.EmployeeManagement.service;

import com.EmployeeManagement.model.Department;
import com.EmployeeManagement.model.ResponseData;
import com.EmployeeManagement.repository.DepartmentRepository;
import com.EmployeeManagement.repository.EmployeeRepository;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DepartmentServiceTests {

    @Autowired
    DepartmentService departmentService;
    // InjectMocks will create a real object for EmployeeService and inject all
    // dependencies
    // such as EmployeeRepository to it.

    @InjectMocks
    DepartmentService departmentServiceMockito;

    // Mock will create a mock object of DepartmentRerpository

    @Mock
    DepartmentRepository departmentRepository;

    @Mock
    EmployeeRepository employeeRepository;

    @Mock
    List<Department> mockedList;

    // Spy is like a partial mock, which will track the interactions with the object like a mock. Additionally, it allows us to call all the normal methods of the object. Whenever we call a method of the spy object, the real method will be invoked(unless it is stubbed).

    @Spy
    List<Department> spyList = new ArrayList<Department>();

    Department dept = new Department();
    List<Department> departmentList = new ArrayList<>();

    @BeforeEach
    public void init() {
        dept.setDEPARTMENTID(1);
        dept.setDepartmentname("Testing");
    }

    @Test
    @Order(1)
    public void testAddDepartment() {
        when(departmentRepository.save(dept)).thenReturn(dept);
        ResponseEntity<ResponseData> response = departmentServiceMockito.addDepartment(dept);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Department added successfully.", response.getBody().getMessage());
    }


    @Test
    @Order(3)
    public void getAllDepartmentDetailsCase1() {
        departmentList.add(dept);
        mockedList.add(dept);
        Mockito.verify(mockedList).add(dept);
       // assertEquals(0, mockedList.size());
        when(departmentRepository.findAll()).thenReturn(departmentList);
        ResponseEntity<ResponseData> response = departmentServiceMockito.getAllDepartmentDetails();
        assertEquals("Data Found", response.getBody().getMessage());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().getDepartmentList().get(0).getDEPARTMENTID());
        assertEquals("Testing", response.getBody().getDepartmentList().get(0).getDepartmentname());
    }

    @Test
    @Order(4)
    public void testGetAllDepartmentDetailsCase2() {
        departmentList.clear();
        when(departmentRepository.findAll()).thenReturn(departmentList);
        ResponseEntity<ResponseData> response = departmentServiceMockito.getAllDepartmentDetails();
        assertEquals("No Data Found. Department details are not added yet", response.getBody().getMessage());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    @Order(5)
    public void testGetAllDepartmentDetailsCase3() {
        departmentServiceMockito = new DepartmentService();
        ResponseEntity<ResponseData> response = departmentServiceMockito.getAllDepartmentDetails();
        assertEquals("Failed", response.getBody().getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }


    @Test
    @Order(6)
    public void testgetDepartmentByNameCase1() {
        departmentList.add(dept);
        String departmentName = "Testing";
        when(departmentRepository.findByDepartmentname(departmentName)).thenReturn(departmentList);
        ResponseEntity<ResponseData> response = departmentServiceMockito.getDepartmentByName(departmentName);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Searched Department is found", response.getBody().getMessage());
        assertEquals(1, response.getBody().getDepartmentList().get(0).getDEPARTMENTID());
    }

    @Test
    @Order(7)
    public void testgetDepartmentByNameNotFound() {
        String departmentName = "Programming";
        when(departmentRepository.findByDepartmentname(departmentName)).thenReturn(departmentList);
        ResponseEntity<ResponseData> response = departmentServiceMockito.getDepartmentByName(departmentName);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Failed", response.getBody().getMessage());
    }

    @Test
    @Order(8)
    public void testUpdateDepartmentDetailsCase1() {
        dept = null;
        departmentRepository.save(dept);
        // departmentList.add(dept);
        ResponseEntity<ResponseData> response = departmentServiceMockito.updateDepartmentDetails(2, dept);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Failed", response.getBody().getMessage());
    }


    @Test
    @Order(9)
    public void testUpdateDepartmentDetailsCase2() {

        int id = 190;
        dept.setDEPARTMENTID(id);
        dept.setDepartmentname("Training");
        departmentList.add(dept);
        when(departmentRepository.save(dept)).thenReturn(dept);
        ResponseEntity<ResponseData> response = departmentServiceMockito.updateDepartmentDetails(id, dept);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Searched Department ID is not found.", response.getBody().getMessage());
    }

    @Test
    @Order(10)
    public void testupdateAllDepartmentCase1() {
        Department department = new Department();
        List<Department> departmentList = new ArrayList<>();
        department.setDEPARTMENTID(1);
        department.setDepartmentname("Training");
        department.setDEPARTMENTID(2);
        department.setDepartmentname("Recruitment");
        departmentList.add(department);

        when(departmentRepository.save(department)).thenReturn(department);
        ResponseEntity<ResponseData> response = departmentServiceMockito.updateAllDepartment(departmentList);
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals("Department data Updated successfully.", response.getBody().getMessage());
    }

    @Test
    @Order(11)
    public void testGetDepartmentByID() {

        int id = 11;
        when(departmentRepository.findById(id)).thenReturn(null);
        ResponseEntity<ResponseData> response = departmentServiceMockito.getDepartmentByID(id);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Failed", response.getBody().getMessage());
    }

    @Test
    @Order(12)
    public void testGetDepartmentByIDCase2() {
        Department dept = new Department();
        int id = 0;
        when(departmentRepository.findById(anyInt())).thenReturn(Optional.empty());
        ResponseEntity<ResponseData> response = departmentServiceMockito.getDepartmentByID(id);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Data Not found", response.getBody().getMessage());
    }




}
