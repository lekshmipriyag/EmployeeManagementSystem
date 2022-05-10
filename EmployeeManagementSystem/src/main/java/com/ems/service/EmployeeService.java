package com.ems.service;

import com.ems.exceptions.NoDataFoundException;
import com.ems.model.Employee;
import com.ems.model.ResponseData;
import com.ems.repository.DepartmentRepository;
import com.ems.repository.EmployeeRepository;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EmployeeService {
    Logger logger = LoggerFactory.logger(EmployeeService.class);

    @Autowired
    private EmployeeRepository employeeRepository;
    private DepartmentRepository departmentRepository;
    private DepartmentService departmentService;

    ResponseData result = new ResponseData();

    public void responseData() {
        result.setMessage("Failed");
        result.setEmployeeList(null);
        result.setEmployeeDataBasedOnDepartment(null);
        result.setDepartmentList(null);
    }

    // Count Total Number of employee
    public ResponseEntity<ResponseData> employeeStrength() {
        logger.info("Total Count");

            result.setMessage("Total No: of Staff: " + employeeRepository.count());
            return new ResponseEntity<ResponseData>(result, HttpStatus.FOUND);

    }

    public ResponseEntity<ResponseData> getAllEmployeeDetails() {
        try {
            logger.info("Fetch All Employee Data");
            List<Employee> employeeList = new ArrayList<>();
            employeeRepository.findAll().forEach(employeeList::add);
            if (employeeList.isEmpty()) {
                result.setMessage("No Data Found. Employee details are not added yet");
                return new ResponseEntity<ResponseData>(result, HttpStatus.NO_CONTENT);
            } else {
                result.setMessage("Data Found");
                result.setEmployeeList(employeeList);
                return new ResponseEntity<ResponseData>(result, HttpStatus.OK);
            }
        } catch (Exception e) {
            responseData();
            return new ResponseEntity<ResponseData>(result, HttpStatus.BAD_REQUEST);
        }
    }

    // Get employee data based on ID

    public ResponseEntity<ResponseData> getAllEmployeeByID(int Id) {
        List<Employee> employeeListByID = new ArrayList<>();
        try {
            logger.info("Get employee data based on ID");
            Optional<Employee> resultData = employeeRepository.findById(Id);
            if (resultData.isPresent()) {
                employeeListByID.add(resultData.get());
                result.setEmployeeList(employeeListByID);
            }
            if (employeeListByID.isEmpty()) {
                responseData();
                result.setMessage("No Data Found");
                return new ResponseEntity<ResponseData>(result, HttpStatus.NOT_FOUND);
            } else {
                result.setMessage("Searched ID is found");
                return new ResponseEntity<ResponseData>(result, HttpStatus.OK);
            }
        } catch (Exception e) {
            responseData();
            return new ResponseEntity<ResponseData>(result, HttpStatus.BAD_REQUEST);
        }

    }


    // Get employee data by Number
    public ResponseEntity<ResponseData> getEmployeeByNumber(String Number) {
        logger.info("Get employee data by Name");
        try {
            List<Employee> fetchEmployeeByNumber = employeeRepository.findByEmployeeNumber(Number);
            if (fetchEmployeeByNumber.size() == 0) {
                responseData();
                result.setMessage("Searched Employee is not found");
                return new ResponseEntity<ResponseData>(result, HttpStatus.NOT_FOUND);
            } else {
                result.setEmployeeList(fetchEmployeeByNumber);
                result.setMessage("Searched Employee is found");
                return new ResponseEntity<ResponseData>(result, HttpStatus.FOUND);
            }
        } catch (Exception e) {
            responseData();
            return new ResponseEntity<ResponseData>(result, HttpStatus.BAD_REQUEST);
        }

    }


    // Get employee data by Name
    public ResponseEntity<ResponseData> getEmployeeByName(String name) {
        logger.info("Get employee data by Name");
        try {
            List<Employee> fetchEmployeeByName = employeeRepository.findByName(name);
            if (fetchEmployeeByName.size() == 0) {
                responseData();
                result.setMessage("Searched Employee is not found");
                return new ResponseEntity<ResponseData>(result, HttpStatus.NOT_FOUND);
            } else {
                result.setEmployeeList(fetchEmployeeByName);
                result.setMessage("Searched Employee is found");
                return new ResponseEntity<ResponseData>(result, HttpStatus.FOUND);
            }
        } catch (Exception e) {
            responseData();
            return new ResponseEntity<ResponseData>(result, HttpStatus.BAD_REQUEST);
        }

    }

    // Add Multiple employee Details
    /*public ResponseEntity<ResponseData> addEmployeeBatch(Employee employee) {
        logger.info(" Add Multiple employee Details");
        try {
            Employee e1 = new Employee(1, "EMP123", "Jhon", "17/03/1993",
                    "Male", "jhon@gmail.com", "0431440140", "MCA", 3,
                    "23/12/2020", "Melbourne", "Victoria", new Department(5,"Testing"));
            Employee e2 = new Employee(2, "EMP128", "Meera", "17/03/1993", "Male", "meera@gmail.com",
                    "0431440140", "MCA", 3, "23/12/2020", "Melbourne", "Victoria", new Department(5,"Testing"));

            Employee e3 = new Employee(3, "EMP130", "Meera", "17/03/1993", "Male", "meera@gmail.com",
                    "0431440140", "MCA", 3, "23/12/2020", "Melbourne", "Victoria", new Department(3,"Designing"));

            List<Employee> employeeList = Arrays.asList(e1, e2, e3, e3);
            employeeRepository.saveAll(employeeList);
            employeeList.add(employee);
            result.setEmployeeList(employeeList);
            result.setMessage("Employee data added successfully.");
            return new ResponseEntity<ResponseData>(result, HttpStatus.CREATED);
        }catch (Exception e) {
            result.setMessage("Failed");
            return new ResponseEntity<ResponseData>(result, HttpStatus.BAD_REQUEST);
        }
    }*/

    // Add new employee to the table
    public ResponseEntity<ResponseData> addEmployee(Employee employee) {
        logger.info(" Add new employee");
        List<Employee> employeeList = new ArrayList<>();
        try {
            String empNum, email;
            int deptID;
            empNum = employee.getEmployeeNumber();
            deptID = employee.getDepartment().getDEPARTMENTID();
            email = employee.getEmailID();
            // int fetchEmail = employeeRepository.findEmailExists(empNum,email);

//            if(fetchEmail==1){
//                result.setMessage("EmailID Exists");
//                return new ResponseEntity<ResponseData>(result, HttpStatus.NOT_ACCEPTABLE);
//            }
            int departmentExists = employeeRepository.findAnyDepartment(deptID);
            if (departmentExists == 0) {
                responseData();
                result.setMessage("Department Not Exists");
                return new ResponseEntity<ResponseData>(result, HttpStatus.FORBIDDEN);
            }
            int employeeNumberWithDepartment = employeeRepository.findEmployeeNumberAndDepartment(deptID, empNum);

            if (employeeNumberWithDepartment == 0) {

                employeeRepository.save(employee);
                employeeList.add(employee);
                result.setEmployeeList(employeeList);
                result.setMessage("Employee added successfully.");
                return new ResponseEntity<ResponseData>(result, HttpStatus.CREATED);
            } else {
                responseData();
                result.setMessage("Sorry. This employee is already added to the department\n.");
                return new ResponseEntity<ResponseData>(result, HttpStatus.FORBIDDEN);
            }

        } catch (Exception e) {
            responseData();
            return new ResponseEntity<ResponseData>(result, HttpStatus.BAD_REQUEST);
        }
    }

    // Delete employee based on ID
    public ResponseEntity<ResponseData> removeEmployee(int id) {

        logger.info("Delete an Employee by ID- Service");
        try {
            if (employeeRepository.existsById(id)) {
                employeeRepository.deleteById(id);
                responseData();
                result.setMessage("Employee Id " + id + " has been deleted");
                return new ResponseEntity<ResponseData>(result, HttpStatus.OK);
            } else {
                throw new NoDataFoundException();
            }

        } catch (Exception e) {
            responseData();
            result.setMessage("The id is invalid for the employee.");
            return new ResponseEntity<ResponseData>(result, HttpStatus.NOT_FOUND);
        }
    }

    // Delete employees based on the Ids' in the list
    public ResponseEntity<ResponseData> deleteEmployeeList(List<Integer> employeeIDList) {
        logger.info("Delete employee based on a list ");

        try {
            if (employeeIDList.size() >= 1) {
                if (employeeRepository.findAllById(employeeIDList) != null) {
                    employeeRepository.deleteAllById(employeeIDList);
                    responseData();
                    result.setMessage("Mentioned Employee Ids' are deleted successfully");
                    return new ResponseEntity<ResponseData>(result, HttpStatus.OK);
                }
            }
            result.setMessage("The List is empty");
            return new ResponseEntity<ResponseData>(result, HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            responseData();
            result.setMessage("Failed. One of the employee IDs' is not in the DB");
            return new ResponseEntity<ResponseData>(result, HttpStatus.BAD_REQUEST);
        }
    }

    // Delete all employee from the database
    public ResponseEntity<ResponseData> deleteAllEmployeeData() {
        logger.info(" Delete all employee from the database ");
        try {
            long count = employeeRepository.count();
            if (count >= 1) {
                employeeRepository.deleteAll();
                responseData();
                result.setMessage("All Details are deleted.");
                return new ResponseEntity<ResponseData>(result, HttpStatus.OK);
            } else {
                responseData();
                result.setMessage("Table is Empty");
                return new ResponseEntity<ResponseData>(result, HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {
            responseData();
            return new ResponseEntity<ResponseData>(result, HttpStatus.BAD_REQUEST);
        }
    }

}

