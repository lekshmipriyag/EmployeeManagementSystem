package com.EmployeeManagement.controller;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.EmployeeManagement.dao.Employee;
import com.EmployeeManagement.service.EmployeeService;

@RestController
public class RegisterController {

	Logger logger = LoggerFactory.logger(RegisterController.class);

	@Autowired
	private EmployeeService employeeService;
	List<Integer> employeeIDList = new ArrayList<>();

	boolean result;

	@RequestMapping("/getEmployee")
	public ResponseEntity<List<Employee>> getAllEmployees() {
		logger.info("Fetch all employee details - Controller");
		List<Employee> fetchAllEmployee = employeeService.getAllEmployeeDetails();
		if (fetchAllEmployee.size() == 0) {
			return new ResponseEntity<>(fetchAllEmployee, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(fetchAllEmployee, HttpStatus.OK);
	}


	@RequestMapping("/getEmployee/{id}")
	public ResponseEntity<List<Employee>> getEmployeeByID(@PathVariable int id) {
		logger.info("Get Employee Data By ID - Controller");
		List<Employee> fetchEmployeeById = employeeService.getAllEmployeeByID(id);
		if (fetchEmployeeById.size() == 0) {
			return new ResponseEntity<>(fetchEmployeeById, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(fetchEmployeeById, HttpStatus.OK);

	}

	@RequestMapping("/totalCount")
	public String getEmployeeCount() {
		logger.info("Employee Count - Controller");
		long count = employeeService.employeeStrength();
		return "Total No: of Staff: " + count;
	}


	@RequestMapping("/searchName/{name}")
	public ResponseEntity<List<Employee>> getEmployeeByName(@PathVariable String name) {
		logger.info("Get Employee Data By Name - Controller");
		List<Employee> fetchEmployeeByName = employeeService.getEmployeeByName(name);
		if (fetchEmployeeByName.size() == 0) {
			return new ResponseEntity<>(fetchEmployeeByName, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(fetchEmployeeByName, HttpStatus.OK);
	}


	@RequestMapping(method = RequestMethod.POST, value = "/addEmployee")
	public ResponseEntity<Employee> registerEmployee(@Valid @RequestBody Employee employee) {
		logger.info("Add an Employee - Controller");
		employeeService.addEmployee(employee);
		return new ResponseEntity<>( HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/addEmployeeList")
	public String addEmployeeBatch(Employee employee) {
		logger.info("Add Employ Details Batch - Controller");
		result = employeeService.addEmployeeBatch(employee);
		if (result == true) {
			long count = employeeService.employeeStrength();
			return "Employee lists are added successfully.Employee Size " + count;
		} else {
			return "Failed";
		}
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/editEmployee/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable int id, @Valid @RequestBody Employee employee) {
		logger.info("Update an Employee- Controller");
		Employee updatedEmployeeById = employeeService.updateEmployeeDetails(id, employee);
		return new ResponseEntity<>(updatedEmployeeById, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/updateList")
	public String updateSelectedEmployees(@RequestBody List<Employee> employee) {
		logger.info("Update Employee Data Batch- Controller");
		result = employeeService.updateAllEmployee(employee);
		if (result == true) {
			return "Successfully Updated";
		} else {
			return "Failed";
		}

	}


	@RequestMapping(method = RequestMethod.DELETE, value = "/removeEmployee/{id}")
	public String deleteEmployee(@PathVariable("id") int id) {
		logger.info("Delete an Employee by ID- Controller");
		if (id > 0) {
			if (employeeService.removeEmployee(id)) {
				return "Employee Id " + id + " has been deleted";
			} else {
				return "Cannot delete the employee.";
			}
		}
		return "The id is invalid for the employee.";
	}
	//Old Code
	/*@RequestMapping(method = RequestMethod.DELETE, value = "/removeEmployee/{id}")
	public String deleteEmployee(@PathVariable("id") int id) {
		logger.info("Delete an Employee by ID- Controller");
		if (id > 0) {
			if (employeeService.removeEmployee(id) != null) {
				return "Employee Id " + id + " has been deleted";
			} else {
				return "Cannot delete the employee.";
			}
		}
		return "The id is invalid for the employee.";
	}*/

	@RequestMapping(method = RequestMethod.DELETE, value = "/deleteAllEmployees")
	public String deleteAllEmployee() {
		logger.info("Delete All Employee from the DB- Controller");
		result = employeeService.deleteAllEmployeeData();
		if (result == true) {
			return "Employee details are deleted successfully";
		} else {
			return "Cannot delete. The list is empty";
		}

	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/deleteList")
	public String deleteEmployeeList(@RequestBody List<Integer> employeeIDList) {
		logger.info("Delete employee based on a list - Controller");
		result = employeeService.deleteEmployeeList(employeeIDList);
		if (result == true) {
			return "Mentioned Employee Ids' are deleted successfully";
		} else {
			return "Failed. The list is either empty or One of the employee IDs' is not in the DB";
		}
	}

}
