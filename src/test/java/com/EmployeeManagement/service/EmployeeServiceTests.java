package com.EmployeeManagement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.EmployeeManagement.dao.Employee;
import com.EmployeeManagement.repository.EmployeeRepository;

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

	Employee emp = new Employee();
	List<Employee> employeeList = new ArrayList<>();

	public Employee inputData() {
		emp.setName("Arun");
		emp.setDob("17/03/1993");
		emp.setEmailID("arun@gmail.com");
		emp.setExperience(3);
		emp.setQualification("MCA");
		emp.setJoiningDate("08/12/2021");
		return emp;
	}

	@Test
	@Order(1)
	public void addEmptyEmployee() {
		Employee emp = null;
		Mockito.when(employeeRepository.save(emp)).thenReturn(null);
		ResponseEntity<Employee> response = employeeService.addEmployee(emp);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	@Order(2)
	public void addEmployeeWithoutName() {
		emp.setName("");
		emp.setDob("17/03/1993");
		emp.setEmailID("arun@gmail.com");
		emp.setExperience(3);
		emp.setQualification("MCA");
		emp.setJoiningDate("08/12/2021");
		Mockito.when(employeeRepository.save(emp)).thenReturn(null);
		ResponseEntity<Employee> response = employeeService.addEmployee(emp);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	@Order(3)
	public void registerEmployee() {

		Employee emp = new Employee();
		emp.setName("Alex");
		emp.setDob("17/03/1993");
		emp.setEmailID("alex@gmail.com");
		emp.setExperience(3);
		emp.setQualification("MCA");
		emp.setJoiningDate("08/12/2021");
		Mockito.when(employeeRepository.save(emp)).thenReturn(emp);
		ResponseEntity<Employee> response = employeeService.addEmployee(emp);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}

	@Test
	@Order(4)
	public void getAllEmployeeDetails() {
		 emp = inputData();
		employeeList.add(emp);
		Mockito.when(employeeRepository.findAll()).thenReturn(employeeList);
		List<Employee> response = employeeServiceMockito.getAllEmployeeDetails();
		assertEquals("Arun", response.get(0).getName());
		assertEquals("arun@gmail.com", response.get(0).getEmailID());
	}

	@Test
	@Order(5)
	public void getAllEmployeeByID() {
		Optional<Employee> emp = Optional.of(inputData());
		Mockito.when(employeeRepository.findById(1)).thenReturn(emp);
		Mockito.when(employeeRepository.findById(23)).thenReturn(null);
		List<Employee> response = employeeServiceMockito.getAllEmployeeByID(1);
		assertEquals("17/03/1993", response.get(0).getDob());
		assertEquals("08/12/2021", response.get(0).getJoiningDate());
	}

	@Test
	@Order(6)
	public void getEmployeeByName() {
		employeeList.add(inputData());
		Mockito.when(employeeRepository.findByName("Arun")).thenReturn(employeeList);
		Mockito.when(employeeRepository.findByName("Lekshmi")).thenReturn(null);
		List<Employee> response = employeeServiceMockito.getEmployeeByName("Arun");
		assertEquals("17/03/1993", response.get(0).getDob());
		assertEquals("arun@gmail.com", response.get(0).getEmailID());
	}

	@Test
	@Order(7)
	public void getEmployeeStrength() {
		long count = 1;
		long response;
		Mockito.when(employeeRepository.count()).thenReturn(count);
		response = employeeServiceMockito.employeeStrength();
		assertEquals(1, response);
	}

	@Test
	@Order(8)
	public void addEmployeeBatch() {
		emp = new Employee();
		boolean response;
		emp.setName("Jeni George");
		emp.setDob("17/03/1995");
		emp.setEmailID("JeniGeorge@gmail.com");
		emp.setExperience(0);
		emp.setQualification("Masters Of IT");
		emp.setJoiningDate("08/12/2021");
		employeeList.add(emp);

		emp.setName("Lekshmi Geetha");
		emp.setDob("17/05/1995");
		emp.setEmailID("lekshmigeethaa@gmail.com");
		emp.setExperience(3);
		emp.setQualification("Masters of IT");
		emp.setJoiningDate("24/12/2021");
		employeeList.add(emp);

		Mockito.when(employeeRepository.save(emp)).thenReturn(emp);
		response = employeeServiceMockito.addEmployeeBatch(emp);
		assertEquals(true, response);
		response = employeeServiceMockito.addEmployeeBatch(null);
		assertEquals(false, response);
	}

	@Test
	@Order(9)
	public void updateAllEmployee() {
		emp = new Employee();
		boolean response;
		emp.setName("Jeni George");
		emp.setDob("17/03/1995");
		emp.setEmailID("JeniGeorge@gmail.com");
		emp.setExperience(0);
		emp.setQualification("Masters Of IT");
		emp.setJoiningDate("08/12/2021");
		employeeList.add(emp);

		emp.setName("Lekshmi Geetha");
		emp.setDob("17/05/1995");
		emp.setEmailID("lekshmigeethaa@gmail.com");
		emp.setExperience(3);
		emp.setQualification("Masters of IT");
		emp.setJoiningDate("24/12/2021");
		employeeList.add(emp);
		Mockito.when(employeeRepository.saveAll(employeeList)).thenReturn(employeeList);
		response = employeeServiceMockito.updateAllEmployee(employeeList);
		assertEquals(true, response);
	}

	@Test
	@Order(10)
	public void updateEmployeeDetailsByID() {
		emp =inputData();
		emp.setName("Stephen");
		emp.setEmailID("stephen@iGreenData.com");
		employeeList.add(emp);
		Mockito.when(employeeRepository.saveAll(employeeList)).thenReturn(employeeList);
		Employee response = employeeServiceMockito.updateEmployeeDetails(1, emp);
		assertEquals("Stephen", response.getName());
		assertEquals("stephen@iGreenData.com", response.getEmailID());
		assertEquals(3, response.getExperience());
		assertEquals("17/03/1993", response.getDob());
	}


	@Test
	@Order(11)
	public void deleteEmployeeById() {
		boolean response;
		int ID =10;
		response = employeeServiceMockito.removeEmployee(ID);
        assertEquals(false, response);
	}
}
