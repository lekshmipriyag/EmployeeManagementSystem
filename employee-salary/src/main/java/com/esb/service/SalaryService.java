package com.esb.service;

import com.esb.model.ResponseData;
import com.esb.model.Salary;
import com.esb.repository.SalaryRepository;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SalaryService {
    Logger logger = LoggerFactory.logger(SalaryService.class);

    @Autowired
    SalaryRepository salaryRepository;

    ResponseData result = new ResponseData();


    @Value("${server.url}")
    private String employeeURL;

   // String employeeURL = "http://localhost:8081/employee/searchEmployeeNumber/";
    RestTemplate restTemplate = new RestTemplate();

    public ResponseEntity<ResponseData> addSalary(Salary salary) {

        try {
            String salaryEmployee, statusCode;
            int salaryAdded = 0;
            double allowance, baseSalary, grandTotal;
            ResponseEntity<String> employeeIsFound;
            salaryEmployee = salary.getEmployeeNumber();
            allowance = salary.getAllowance();
            baseSalary = salary.getBasicSalary();
            grandTotal = allowance + baseSalary;
            salary.setTotal(grandTotal);

            salaryAdded = salaryRepository.isEmployeeSalary(salaryEmployee);
            logger.info("Employee data added " + salaryAdded);
           // employeeIsFound = restTemplateBuilder.build().getForEntity(
            //        employeeURL + salaryEmployee,
            //        String.class);

            employeeIsFound = restTemplate.getForEntity(employeeURL + salaryEmployee, String.class);

            statusCode = String.valueOf(employeeIsFound.getStatusCode());

            logger.info("Status Code " + statusCode + " Employee Number " + salaryEmployee);

            result.setStatus(statusCode);
            if (salaryAdded == 0) {
                if (result.getStatus().equals("302 FOUND")) {
                    result.setMessage("Success");
                    salaryRepository.save(salary);
                    return new ResponseEntity<ResponseData>(result, HttpStatus.OK);
                } else {
                    result.setMessage("Employee Number is not Found");
                    result.setStatus("404 Not Found");
                    return new ResponseEntity<ResponseData>(result, HttpStatus.FORBIDDEN);
                }
            } else {
                result.setMessage("Salary details of this employee exists");
                result.setStatus("Service Unavailable");
                return new ResponseEntity<ResponseData>(result, HttpStatus.FORBIDDEN);
            }


        } catch (Exception e) {
            result.setMessage("Failed");
            result.setStatus("Employee is not found");
            return new ResponseEntity<ResponseData>(result, HttpStatus.BAD_REQUEST);
        }
    }

}
