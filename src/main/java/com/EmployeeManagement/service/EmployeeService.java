package com.EmployeeManagement.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.EmployeeManagement.controller.RegisterController;
import com.EmployeeManagement.dao.Employee;
import com.EmployeeManagement.repository.EmployeeRepository;

@Service
public class EmployeeService {

	Logger logger = LoggerFactory.logger(RegisterController.class);

	@Autowired
	private EmployeeRepository employeeRepository;

	// List all employee data
	public List<Employee> getAllEmployeeDetails() {

		logger.info("Fetch all employee details - Service");

		List<Employee> employeeList = new ArrayList<>();
		employeeRepository.findAll().forEach(employeeList::add);

		return employeeList;
	}


	// Get employee data based on ID
	public List<Employee> getAllEmployeeByID(int Id) {

		logger.info("Get Employee Data By ID - Service");

		Optional<Employee> result = employeeRepository.findById(Id);
		List<Employee> employeeListByID = new ArrayList<>();
		if (result.isPresent()) {
			employeeListByID.add(result.get());
        }
		 return employeeListByID;
	}

	// Get employee data by Name
	public List<Employee> getEmployeeByName(String name) {

		logger.info("Get Employee Data By Name - Service");
		return employeeRepository.findByName(name);
	}

	// Count Total Number of employee
	public long employeeStrength() {

		logger.info("Employee Count - Service");

		long count = employeeRepository.count();
		return count;
	}

	// Add new employee to the table
	public ResponseEntity<Employee> addEmployee(Employee employee) {

		logger.info("Add an Employee - Service");

		try {
			if(employee == null || employee.getName() == ""
			   || employee.getDateofjoining() == "" || employee.getDob() == ""
			   || employee.getEmailid() == "") {
				return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
			}
			employeeRepository.save(employee);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return new ResponseEntity<>( HttpStatus.CREATED);

	}

	// Add Multiple employee Details
	public boolean addEmployeeBatch(Employee employee) {

		logger.info("Add Employ Details Batch- Service");

		boolean result = false;
		try {
			long count = employeeRepository.count();
			int i = 0;
			if (count == 0)
				i = 1;
			else
				i = (int) count + 1;

			for (; i <= 100; i++) {
				int id = employee.getId();
				employee.setName("User" + id);
				employee.setDob("17/03/1997");
				employee.setQualification("BTech");
				employee.setExperience(3);
				employee.setDateofjoining("08/12/2021");
				employee.setEmailid("user" + id + "@gmail.com");
				employeeRepository.save(employee);
				result = true;
			}
		} catch (Exception e) {
			System.out.println();
			return false;
		}
		return result;

	}

	// Update Employee data based on ID
	public Employee updateEmployeeDetails(int id, Employee employee) {

		logger.info("Update an Employee- Service");

		try {
			employeeRepository.save(employee);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return employee;
	}

	// Update List of employees' at a time (old)
	public boolean updateAllEmployee(List<Employee> employee) {

		logger.info("Update Employee Data Batch- Service");

		try {
			employeeRepository.saveAll(employee);
			return true;

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}

	}

	// Delete all employee from the database
	public boolean deleteAllEmployeeData() {

		logger.info("Delete All Employee from the DB- Service");

		try {
			long count = employeeRepository.count();
			if (count >= 1) {
				employeeRepository.deleteAll();
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}


	// Delete employee based on ID
		public boolean removeEmployee(int id) {

			logger.info("Delete an Employee by ID- Service");

			try {
				if(employeeRepository.existsById(id))
				{
					employeeRepository.deleteById(id);
					return true;
				}else {
					return false;
				}

			} catch (Exception e) {
				System.out.println(e.getMessage());
				return false;
			}
		}

		// Delete employee based on ID old Code

	/*public ResponseEntity<Employee> removeEmployee(int id) {

		logger.info("Delete an Employee by ID- Service");

		try {
			if(employeeRepository.existsById(id)) {
				employeeRepository.deleteById(id);
			}else {
				return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>( HttpStatus.OK);
	}*/

	// Delete employees based on the Ids' in the list
	public boolean deleteEmployeeList(List<Integer> employeeIDList) {

		logger.info("Delete employee based on a list - Service");

		boolean result = false;
		try {
			if (employeeIDList.size() >= 1) {
				if (employeeRepository.findAllById(employeeIDList) != null) {
					employeeRepository.deleteAllById(employeeIDList);
					result = true;
				}
			}

		} catch (Exception e) {
			e.getMessage();
			result = false;
		}
		return result;

	}

}
