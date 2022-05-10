package com.esb.controller;

import com.esb.model.ResponseData;
import com.esb.model.Salary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.esb.service.SalaryService;

import javax.validation.Valid;

@RestController
@RequestMapping("/salary")
public class SalaryController {

    RestTemplate restTemplate = new RestTemplate();

    @Autowired
    SalaryService salaryService;

    @RequestMapping("/welcome")
    public String welcome() {
        return "Employee Salary page";
    }

    @RequestMapping("/getData")
    public ResponseEntity<String> getDataByEmployee() {
        return restTemplate.getForEntity("http://localhost:8081/employee/searchEmployeeNumber/EMP450/",
                String.class);

    }

    @RequestMapping(method = RequestMethod.POST, value = "/addSalary")
    public ResponseEntity<ResponseData> addSalary(@Valid @RequestBody Salary salary) {
        return salaryService.addSalary(salary);
    }
}
