package com.EmployeeManagement.service;


import com.EmployeeManagement.exceptions.NoDataFoundException;
import com.EmployeeManagement.model.Department;
import com.EmployeeManagement.model.ResponseData;
import com.EmployeeManagement.repository.DepartmentRepository;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {
    Logger logger = LoggerFactory.logger(EmployeeService.class);

    @Autowired
    private DepartmentRepository departmentRepository;

    ResponseData result = new ResponseData();

    public void responseData() {
        result.setMessage("Failed");
        result.setEmployeeList(null);
        result.setEmployeeDataBasedOnDepartment(null);
        result.setDepartmentList(null);
    }

    // Add new employee to the table
    public ResponseEntity<ResponseData> addDepartment(Department department) {
        logger.info(" Add new Department");
        List<Department> departmentList = new ArrayList<>();
        long departmentCount =  departmentRepository.count();
        try{
            if(departmentCount == 0){
                Department d1 = new Department(1, "Human Resources");
                Department d2 = new Department(2, "Back-End Development");
                Department d3 = new Department (3, "Designing");
                Department d4 = new Department(4, "Front-End Development");
                Department d5 = new Department(5, "Testing");
                Department d6 = new Department(6, "Finance");
                Department d7 = new Department(7, "Marketing");

                departmentList= Arrays.asList(d1,d2,d3,d4,d5,d6,d7);
                departmentRepository.saveAll(departmentList);
                result.setDepartmentList(null);
                result.setMessage("Department added successfully.");
                return new ResponseEntity<ResponseData>(result, HttpStatus.CREATED);
            }else{
                responseData();
                result.setMessage("Sorry!!!!!. Admin is not allowed to add a new Department");
                return new ResponseEntity<ResponseData>(result, HttpStatus.FORBIDDEN);
            }

        }catch (Exception e) {
            responseData();
            return new ResponseEntity<ResponseData>(result, HttpStatus.BAD_REQUEST);
        }
    }

    // Get department data by Name
    public ResponseEntity<ResponseData> getDepartmentByID(int Id) {
        logger.info("Get department data based on ID");
        try {
            Optional<Department> resultData = departmentRepository.findById(Id);
            List<Department> departmentData = new ArrayList<>();
            if (resultData.isPresent()) {
                departmentData.add(resultData.get());
                result.setMessage("Data found");
                result.setDepartmentList(departmentData);
                return new ResponseEntity<ResponseData>(result, HttpStatus.OK);
            } else {
                responseData();
                result.setMessage("Data Not found");
                return new ResponseEntity<ResponseData>(result, HttpStatus.NOT_FOUND);
            }


        } catch (Exception e) {
            responseData();
            return new ResponseEntity<ResponseData>(result, HttpStatus.BAD_REQUEST);
        }

    }


    // Update Employee data based on ID
    public ResponseEntity<ResponseData> updateDepartmentDetails(int id, Department department) {
        logger.info(" Update department data based on ID ");
        try {
            List<Department> deployeeList = new ArrayList<>();
            List<Department> departmentNames = departmentRepository.findByDepartmentname(department.getDepartmentname());
            if (departmentRepository.existsById(id)) {
                if (department.getDEPARTMENTID() != id) {
                    responseData();
                    result.setMessage("Searched Department ID is not equal with the Id you tried to update.");
                    return new ResponseEntity<ResponseData>(result, HttpStatus.FORBIDDEN);
                }
                if (departmentNames.size() == 1) {
                    responseData();
                    result.setMessage("Department name exists.");
                    return new ResponseEntity<ResponseData>(result, HttpStatus.FORBIDDEN);
                }
                departmentRepository.save(department);
                deployeeList.add(department);
                result.setDepartmentList(deployeeList);
                result.setMessage("Department data Updated successfully.");
                return new ResponseEntity<ResponseData>(result, HttpStatus.ACCEPTED);
            } else {
                responseData();
                result.setMessage("Searched Department ID is not found.");
                return new ResponseEntity<ResponseData>(result, HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            responseData();
            return new ResponseEntity<ResponseData>(result, HttpStatus.BAD_REQUEST);
        }
    }

    // Update List of department' at a time
    public ResponseEntity<ResponseData> updateAllDepartment(List<Department> department) {
        logger.info(" Update List of employees ");
            departmentRepository.saveAll(department);
            result.setDepartmentList(department);
            result.setMessage("Department data Updated successfully.");
            return new ResponseEntity<ResponseData>(result, HttpStatus.ACCEPTED);
    }

    //Get all department
    public ResponseEntity<ResponseData> getAllDepartmentDetails() {
        logger.info("Fetch All Department Data");
        try {
            List<Department> departmentList = new ArrayList<>();
            departmentRepository.findAll().forEach(departmentList::add);
            if (departmentList.size() == 0) {
                responseData();
                result.setMessage("No Data Found. Department details are not added yet");
                return new ResponseEntity<ResponseData>(result, HttpStatus.NO_CONTENT);
            } else {
                result.setMessage("Data Found");
                result.setDepartmentList(departmentList);
                return new ResponseEntity<ResponseData>(result, HttpStatus.OK);
            }
        } catch (Exception e) {
            responseData();
            return new ResponseEntity<ResponseData>(result, HttpStatus.BAD_REQUEST);
        }

    }

    // Get department data by Name
    public ResponseEntity<ResponseData> getDepartmentByName(String name) {
        logger.info("Get department data by Name");
        try {
            List<Department> fetchDepartmentByName = departmentRepository.findByDepartmentname(name);

            List<String> fetchEmployeeData = departmentRepository.findEmployeeDetails(name);
            if (fetchDepartmentByName.isEmpty()) {
                throw new NoDataFoundException();
            } else {
                result.setEmployeeDataBasedOnDepartment(fetchEmployeeData);
                result.setDepartmentList(fetchDepartmentByName);
                result.setMessage("Searched Department is found");
                return new ResponseEntity<ResponseData>(result, HttpStatus.OK);
            }
        } catch (Exception e) {
            responseData();
            return new ResponseEntity<ResponseData>(result, HttpStatus.BAD_REQUEST);
        }

    }

    // Delete Department based on ID
    public ResponseEntity<ResponseData> removeDepartment(int id) {

        logger.info("Delete an Department by ID- Service");
        int deptUsed = 0;
        try {
            if (departmentRepository.existsById(id)) {
                deptUsed = departmentRepository.isDepartmentUsed(id);
                if (deptUsed == 0) {
                    departmentRepository.deleteById(id);
                    responseData();
                    result.setMessage("Department Id " + id + " has been deleted");
                    return new ResponseEntity<ResponseData>(result, HttpStatus.OK);
                } else {
                    responseData();
                    result.setMessage("Please delete the employee first");
                    return new ResponseEntity<ResponseData>(result, HttpStatus.FORBIDDEN);
                }

            } else {
                throw new NoDataFoundException();
            }

        } catch (Exception e) {
            responseData();
            return new ResponseEntity<ResponseData>(result, HttpStatus.BAD_REQUEST);
        }
    }

}

