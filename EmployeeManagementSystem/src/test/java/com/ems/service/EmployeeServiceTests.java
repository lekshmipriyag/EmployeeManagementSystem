package com.ems.service;

import com.ems.model.Department;
import com.ems.model.Employee;
import com.ems.model.ResponseData;
import com.ems.repository.DepartmentRepository;
import com.ems.repository.EmployeeRepository;
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
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmployeeServiceTests {

    @Autowired
    EmployeeService employeeService;

    // InjectMocks will create a real object for EmployeeService and inject all
    // dependencies
    // such as EmployeeRepository to it.

    @InjectMocks
    EmployeeService employeeServiceMockito;

    // Mock will create a mock object of EmployeeRepository

    @Mock
    EmployeeRepository employeeRepository;

    @Mock
    DepartmentRepository departmentRepository;

    @Mock
    List<Employee> mockedList;

    // Spy is like a partial mock, which will track the interactions with the object like a mock. Additionally, it allows us to call all the normal methods of the object. Whenever we call a method of the spy object, the real method will be invoked(unless it is stubbed).

    @Spy
    List<Employee> spyList = new ArrayList<Employee>();

    Department dept = new Department();
    Employee employee = new Employee();
    List<Employee> employeeList = new ArrayList<>();

    @BeforeEach
    public void init(){
        departmentData();
        employee.setName("User" + 1);
        employee.setEmployeeNumber("EMP123");
        employee.setDob("17/03/1997");
        employee.setGender("Male");
        employee.setEmailID("user" + 1 + "@gmail.com");
        employee.setMobile("431440140");
        employee.setQualification("BTech");
        employee.setExperience(3);
        employee.setJoiningDate("08/12/2021");
        employee.setCity("melbourne");
        employee.setState("VIC");
        dept.setDEPARTMENTID(1);
        dept.setDepartmentname("Human Resources");
        employee.setDepartment(dept);
    }

    public Department departmentData() {
        dept.setDEPARTMENTID(1);
        dept.setDepartmentname("Human Resources");
        return dept;
    }

    @Test
    @Order(1)
    public void testAddEmployee() {
        Mockito.when(employeeRepository.save(employee)).thenReturn(employee);
        ResponseEntity<ResponseData> response = employeeServiceMockito.addEmployee(employee);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("Department Not Exists", response.getBody().getMessage());
    }

    @Test
    @Order(2)
    public void testAddEmployeeCase2() {
        Mockito.when(employeeRepository.save(any())).thenReturn(null);
        ResponseEntity<ResponseData> response = employeeServiceMockito.addEmployee(null);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
         assertEquals("Failed", response.getBody().getMessage());
    }

    @Test
    @Order(3)
    public void getAllEmployeeDetailsMockAnnotation() {
        employeeList.add(employee);
        mockedList.add(employee);
        Mockito.verify(mockedList).add(employee);
        assertEquals(0, mockedList.size());

        Mockito.when(employeeRepository.findAll()).thenReturn(employeeList);
        ResponseEntity<ResponseData> response = employeeServiceMockito.getAllEmployeeDetails();
        assertEquals("Data Found", response.getBody().getMessage());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User1", response.getBody().getEmployeeList().get(0).getName());
        assertEquals("EMP123", response.getBody().getEmployeeList().get(0).getEmployeeNumber());
        assertEquals(1, response.getBody().getEmployeeList().get(0).getDepartment().getDEPARTMENTID());
        assertEquals("Human Resources", response.getBody().getEmployeeList().get(0).getDepartment().getDepartmentname());
    }


   @Test
   @Order(4)
    public void getAllEmployeeDetailsCase2() {
       employeeList.clear();
       Mockito.when(employeeRepository.findAll()).thenReturn(employeeList);
       ResponseEntity<ResponseData> response = employeeServiceMockito.getAllEmployeeDetails();
       assertEquals("No Data Found. Employee details are not added yet", response.getBody().getMessage());
       assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
   }

    @Test
    @Order(5)
    public void getAllEmployeeDetailsCase3() {
        employeeList = null;
        Mockito.when(employeeRepository.findAll()).thenReturn(employeeList);
        ResponseEntity<ResponseData> response = employeeServiceMockito.getAllEmployeeDetails();
        assertEquals("Failed", response.getBody().getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

//    @Test
//    @Order(6)
//    public void UsingSPYAnnotation() {
//        //List<Employee> spyList = Mockito.spy(new ArrayList<Employee>());
//        employee.setName("User" + 1);
//        employee.setEmployeeNumber("EMP123");
//        employee.setDob("17/03/1997");
//        employee.setGender("Male");
//        employee.setEmailID("user" + 1 + "@gmail.com");
//        employee.setMobile("431440140");
//        employee.setQualification("BTech");
//        employee.setExperience(3);
//        employee.setJoiningDate("08/12/2021");
//        employee.setCity("melbourne");
//        employee.setState("VIC");
//        dept.setDEPARTMENTID(1);
//        dept.setDepartmentname("Human Resources");
//        employee.setDepartment(dept);
//        spyList.add(employee);
//        Mockito.verify(spyList).add(employee);
//        assertEquals(1, spyList.size());
//    }

    @Test
    @Order(7)
    public void testGetEmployeeByIDCase1() {
        int id = 1;
        employeeServiceMockito = new EmployeeService();
        ResponseEntity<ResponseData> response = employeeServiceMockito.getAllEmployeeByID(id);
        assertEquals("Failed", response.getBody().getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Order(8)
    public void testGetEmployeeByIDCase2() {
        int id = 10;
        ResponseEntity<ResponseData> response = employeeServiceMockito.getAllEmployeeByID(id);
        Optional<Employee> emp = Optional.of(employee);
        Mockito.when(employeeRepository.findById(id)).thenReturn(emp);
        assertEquals("No Data Found", response.getBody().getMessage());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @Order(9)
    public void getEmployeeByName()  {
        employeeList.add(employee);
        Mockito.when(employeeRepository.findByName("User1")).thenReturn(employeeList);
        ResponseEntity<ResponseData> response = employeeServiceMockito.getEmployeeByName("User1");
        assertEquals("User1", response.getBody().getEmployeeList().get(0).getName());
        assertEquals("EMP123", response.getBody().getEmployeeList().get(0).getEmployeeNumber());
        assertEquals(HttpStatus.FOUND, response.getStatusCode());
    }

    @Test
    @Order(10)
    public void testGetEmployeeCount() {
        testAddEmployee();
        ResponseEntity<ResponseData> response = employeeServiceMockito.employeeStrength();
        Mockito.when(employeeRepository.count()).thenReturn(1L);
    }

    @Test
    @Order(11)
    public void deleteEmployeeById() {
        int ID =88;
        ResponseEntity<ResponseData> response  = employeeServiceMockito.removeEmployee(ID);
        assertEquals("The id is invalid for the employee.", response.getBody().getMessage());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @Order(12)
    public void testDeleteAllEmployee1() {
        ResponseEntity<ResponseData> response = employeeServiceMockito.deleteAllEmployeeData();
        assertEquals("Table is Empty", response.getBody().getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Order(13)
    public void getEmployeeByNameCase2()  {
        Mockito.when(employeeRepository.findByName("User2")).thenReturn(null);
        ResponseEntity<ResponseData> response = employeeServiceMockito.getEmployeeByName("User2");
         assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Order(14)
    public void testDeletedeleteEmployeeList() {
        List<Integer> idList = new ArrayList<>();
        idList.add(1);
        ResponseEntity<ResponseData> response = employeeServiceMockito.deleteEmployeeList(idList);
        assertEquals("Mentioned Employee Ids' are deleted successfully", response.getBody().getMessage());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Order(15)
    public void testDeleteAllEmployeeCase3() {
        ResponseEntity<ResponseData> response = employeeServiceMockito.deleteAllEmployeeData();
        assertEquals("Table is Empty", response.getBody().getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Order(16)
    public void testDeleteAllEmployeeCase4() {
        employeeServiceMockito = new EmployeeService();
        ResponseEntity<ResponseData> response = employeeServiceMockito.deleteAllEmployeeData();
        assertEquals("Failed", response.getBody().getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Order(17)
    public void getEmployeeByNumberCase1()  {
        employeeList.add(employee);
        Mockito.when(employeeRepository.findByEmployeeNumber("EMP123")).thenReturn(employeeList);
        ResponseEntity<ResponseData> response = employeeServiceMockito.getEmployeeByNumber("EMP123");
        assertEquals("User1", response.getBody().getEmployeeList().get(0).getName());
        assertEquals("EMP123", response.getBody().getEmployeeList().get(0).getEmployeeNumber());
        assertEquals(HttpStatus.FOUND, response.getStatusCode());
    }

    @Test
    @Order(18)
    public void getEmployeeByNumberCase2()  {
        employeeList.add(employee);
        Mockito.when(employeeRepository.findByEmployeeNumber("EMP109")).thenReturn(employeeList);
        ResponseEntity<ResponseData> response = employeeServiceMockito.getEmployeeByNumber("EMP123");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @Order(19)
    public void getEmployeeByNumberCase3()  {
        Mockito.when(employeeRepository.findByEmployeeNumber("EMP109")).thenReturn(null);
        ResponseEntity<ResponseData> response = employeeServiceMockito.getEmployeeByNumber("EMP109");
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

}
