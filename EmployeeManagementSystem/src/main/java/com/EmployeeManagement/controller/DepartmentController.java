package com.EmployeeManagement.controller;

import com.EmployeeManagement.model.Department;
import com.EmployeeManagement.model.ResponseData;
import com.EmployeeManagement.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;


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
