package com.EmployeeManagement.controller;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.EmployeeManagement.dao.Employee;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RegistercontrollerTests {

	@Autowired
	RegisterController registerController;

	@Test
	@Order(1)
	public void testRegisterEmployee() {
		Employee emp = new Employee();
		emp.setName("Alex");
		emp.setDob("17/03/1993");
		emp.setEmailID("alex@gmail.com");
		emp.setExperience(3);
		emp.setQualification("MCA");
		emp.setJoiningDate("08/12/2021");
		ResponseEntity<Employee> response = registerController.registerEmployee(emp);
		System.out.println(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());

	}

	@Test
	@Order(2)
	public void testGetEmployeeByID() {
		int id = 1;
		ResponseEntity<List<Employee>> response = registerController.getEmployeeByID(id);
		System.out.println(response);
		assertEquals(1, response.getBody().get(0).getId());
		assertEquals("Alex", response.getBody().get(0).getName());
		assertEquals("08/12/2021", response.getBody().get(0).getJoiningDate());
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	@Order(3)
	public void testGetAllEmployees() {
		ResponseEntity<List<Employee>> response = registerController.getAllEmployees();
		System.out.println(response);
		assertEquals(1, response.getBody().get(0).getId());
		assertEquals("Alex", response.getBody().get(0).getName());
		assertEquals("08/12/2021", response.getBody().get(0).getJoiningDate());
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	@Order(4)
	public void testGetEmployeeByName() {
		String name = "Alex";
		ResponseEntity<List<Employee>> response = registerController.getEmployeeByName(name);
		System.out.println(response);
		assertEquals(1, response.getBody().get(0).getId());
		assertEquals(3, response.getBody().get(0).getExperience());
		assertEquals("17/03/1993", response.getBody().get(0).getDob());
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	@Order(5)
	public void testGetEmployeeCount() {
		String response = registerController.getEmployeeCount();
		System.out.println(response);
		assertEquals("Total No: of Staff: 1", response);
	}

	@Test
	@Order(6)
	public void testaddEmployeeBatch() {
		Employee emp = new Employee();
		emp.setName("Jeni");
		emp.setDob("17/03/1993");
		emp.setEmailID("Jeni@gmail.com");
		emp.setExperience(5);
		emp.setQualification("MTech");
		emp.setJoiningDate("08/12/2021");
		String response = registerController.addEmployeeBatch(emp);
		System.out.println(response);
		assertEquals(2, emp.getId());
		assertEquals("Employee lists are added successfully.Employee Size 2", response);
	}

	@Test
	@Order(7)
	public void testUpdateEmployee() {
		Employee emp = new Employee();
		int id = 1;
		emp.setName("Lekshmi");
		emp.setDob("23/05/1999");
		emp.setEmailID("lekshmi@gmail.com");
		emp.setExperience(5);
		emp.setQualification("MCA");
		emp.setJoiningDate("08/12/2021");
		ResponseEntity<Employee> response = registerController.updateEmployee(id, emp);
		System.out.println("Response " + response);
		assertEquals("23/05/1999", response.getBody().getDob());
		assertEquals("Lekshmi", response.getBody().getName());
		assertEquals("lekshmi@gmail.com", response.getBody().getEmailID());
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}

	@Test
	@Order(8)
	public void testUpdateSelectedEmployees() {
		List<Employee> employeeList = new ArrayList<>();
		Employee emp = new Employee();
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
		String response = registerController.updateSelectedEmployees(employeeList);
		System.out.println("Response " + response);
		assertEquals("Lekshmi Geetha", employeeList.get(0).getName());
		assertEquals("lekshmigeethaa@gmail.com", employeeList.get(0).getEmailID());
	}

	@Test
	@Order(9)
	public void testDeleteEmployee() {
		int id;
		String response;
		id = 1;
		response = registerController.deleteEmployee(id);
		assertEquals("Employee Id 1 has been deleted", response);
	}

	@Test
	@Order(10)
	public void testDeleteEmployeeList() {
		List<Integer> idList = new ArrayList<>();
		String response;
		idList.add(1);
		idList.add(5);
		response = registerController.deleteEmployeeList(idList);
		System.out.println("Response " + response);
		assertEquals("Failed. The list is either empty or One of the employee IDs' is not in the DB", response);
	}

	@Test
	@Order(11)
	public void testDeleteAllEmployee() {

		String response = registerController.deleteAllEmployee();
		System.out.println("Response " + response);
		assertEquals("Employee details are deleted successfully", response);
	}
}
