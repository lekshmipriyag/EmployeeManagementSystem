package com.ems.controller;

import com.ems.model.Department;
import com.ems.model.ResponseData;
import com.ems.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;


    @RequestMapping("/welcome")
    public String welcome() {
        return "Welcome to version 3 with Microservices";
    }


    @RequestMapping(method = RequestMethod.POST, value = "/addDepartments")
    public ResponseEntity<ResponseData> addDepartment(Department department) {
        return departmentService.addDepartment(department);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/updateDepartment/{id}")
    public ResponseEntity<ResponseData> updateDepartmentDetails(@PathVariable int id, @Valid @RequestBody Department department) {
        return departmentService.updateDepartmentDetails(id, department);
    }


    @RequestMapping("/getAllDepartment")
    public ResponseEntity<ResponseData> getAllDepartment() {
        return departmentService.getAllDepartmentDetails();
    }

    @RequestMapping("/searchDepartment/{name}")
    public ResponseEntity<ResponseData> getDepartmentByName(@PathVariable String name) {
        return departmentService.getDepartmentByName(name);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/deleteDepartment/{id}")
    public ResponseEntity<ResponseData> deleteDepartment(@PathVariable("id") int id) {
        return departmentService.removeDepartment(id);
    }

}
