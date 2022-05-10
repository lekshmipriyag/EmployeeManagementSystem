package com.ems.controller;

import com.ems.model.Employee;
import com.ems.model.ResponseData;
import com.ems.service.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/employee")
@Api(value="Employee Management System", protocols = "http")
public class RegisterController {

    @Autowired
    private EmployeeService employeeService;

    boolean result;

    @RequestMapping("/welcome")
    public String welcome() {
        return "Welcome to version 3 with Microservices";
    }

    @RequestMapping("/totalCount")
    public ResponseEntity<ResponseData> getEmployeeCount() {
        return employeeService.employeeStrength();
    }


    @RequestMapping("/getAllEmployee")
    @ApiOperation(value="Get all employee details from the system.",response = Employee.class,code =200 )
    public ResponseEntity<ResponseData> getAllEmployees() {
        return employeeService.getAllEmployeeDetails();
    }

    @RequestMapping("/getEmployee/{id}")
    public ResponseEntity<ResponseData> getEmployeeByID(@PathVariable int id) {
        return employeeService.getAllEmployeeByID(id);
    }

    @RequestMapping("/searchName/{name}")
    public ResponseEntity<ResponseData> getEmployeeByName(@PathVariable String name)  {
        return employeeService.getEmployeeByName(name);
    }


    @RequestMapping("/searchEmployeeNumber/{number}")
    public ResponseEntity<ResponseData> getEmployeeByNumber(@PathVariable String number)  {
        return employeeService.getEmployeeByNumber(number);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/addEmployee")
    public ResponseEntity<ResponseData> registerEmployee(@Valid @RequestBody Employee employee) {
        return employeeService.addEmployee(employee);
    }
   /* @RequestMapping(method = RequestMethod.POST, value = "/addEmployeeList")
    public ResponseEntity<ResponseData> addEmployeeBatch(Employee employee) {
        return employeeService.addEmployeeBatch(employee);
    }*/

    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteList")
    public ResponseEntity<ResponseData> deleteEmployeeList(@RequestBody List<Integer> employeeIDList) {
        return employeeService.deleteEmployeeList(employeeIDList);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteAllEmployees")
    public ResponseEntity<ResponseData> deleteAllEmployee() {
        return employeeService.deleteAllEmployeeData();
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/removeEmployee/{id}")
    public ResponseEntity<ResponseData> deleteEmployee(@PathVariable("id") int id) {
        return employeeService.removeEmployee(id);
    }

}
