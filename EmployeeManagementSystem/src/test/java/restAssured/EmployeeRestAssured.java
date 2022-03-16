package restAssured;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import net.minidev.json.JSONObject;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasToString;
import static org.testng.Assert.assertEquals;

public class EmployeeRestAssured {
    Logger logger = LoggerFactory.logger(DepartmentRestAssured.class);

    @BeforeTest
    public void init() {
        baseURI = "http://localhost:8081/employee";
    }

    @Test
    public void addDepartment() {
        Response response = RestAssured.get("http://localhost:8081/department/getAllDepartment/");
        int departmentExits = response.getStatusCode();
        if (departmentExits == 204) {
            JSONObject addDepartmentRequest = new JSONObject();
            given().
                    body(addDepartmentRequest.toJSONString()).
                    when().
                    post("http://localhost:8081/department/addDepartments").
                    then().statusCode(201);
        } else {
            assertEquals(response.getStatusCode(), 200);
        }
    }


    @Test
    public void addEmployee() {

        JSONObject requestEmployee = new JSONObject();
        requestEmployee.put("name", "Lekshmi");
        requestEmployee.put("employeeNumber", "EMP400");
        requestEmployee.put("dob", "17/01/1997");
        requestEmployee.put("gender", "Male");
        requestEmployee.put("emailID", "user8@gmail.com");
        requestEmployee.put("mobile", "0431440140");
        requestEmployee.put("qualification", "MCA");
        requestEmployee.put("experience", 6);
        requestEmployee.put("joiningDate", "12/03/2022");
        requestEmployee.put("city", "Melbourne");
        requestEmployee.put("state", "Victoria");

        JSONObject requestDepartment = new JSONObject();
        requestDepartment.put("departmentid", 1);
        requestEmployee.put("department", requestDepartment);
        logger.info(requestEmployee);
        Response response = RestAssured.get(baseURI + "/searchEmployeeNumber/EMP400");
       // int employeeexists = response.getStatusCode();
        if (response.getStatusCode() == 404) {
            given().
                    body(requestEmployee.toJSONString()).contentType("application/json").
                    when().
                    post(baseURI + "/addEmployee").
                    then().statusCode(201).body("message", hasToString("Employee added successfully."));

       } else {
            assertEquals(response.getStatusCode(), 302);
       }
    }


    @Test
    public void getAllEmployee() {
        when().
                get(baseURI + "/getAllEmployee/").
                then().
                statusCode(200).
                body("message", hasToString("Data Found"));

    }

    @Test
    public void getEmployeeByID() {
        //Search By ID
        int ID = 1;
        Response response = RestAssured.get(baseURI + "/getEmployee/{id}", ID);
        if(response.getStatusCode() != 404){
            when().
                    get(baseURI + "/getEmployee/{id}", ID).
                    then().
                    statusCode(200).
                    body("employeeList.id", hasItems(1),
                            "employeeList.city", hasItems("Melbourne"),
                            "message", hasToString("Searched ID is found"));
        }
        else {
            assertEquals(response.getStatusCode(), 404);
        }

    }

    @Test
    public void getEmployeeByName() {
        //Search by name
        String employeeName = "Lekshmi";
        when().
                get(baseURI + "/searchName/{name}", employeeName).
                then().
                statusCode(302).
                body("employeeList.name", hasItems("Lekshmi"),
                        "employeeList.employeeNumber", hasItems("EMP400"),
                        "message", hasToString("Searched Employee is found"));
    }


    @Test
    public void getEmployeeByNameCase2() {
        // Employee Not found
        String employeeName = "Jeniuuuu";
        when().
                get(baseURI + "/searchName/{name}", employeeName).
                then().
                statusCode(404).
                body("message", hasToString("Searched Employee is not found"));

    }

    @Test(priority = 10)
    public void deleteEmployeeByID() {
        //delete employee by iD
        JSONObject request = new JSONObject();

        int employeeID = 1;
        Response response = RestAssured.get(baseURI + "/getEmployee/{id}", employeeID);
        if(response.getStatusCode() != 404){
            given().
                    body(request.toJSONString()).
                    when().
                    delete(baseURI + "/removeEmployee/{id}",employeeID).
                    then().statusCode(200).
                    body("message", hasToString("Employee Id 1 has been deleted"))
                    .log().all();
        }else {
            assertEquals(response.getStatusCode(), 404);
        }

    }

   /* @Test(priority = 11)
    public void deleteAllEmployee() {
        Response response = RestAssured.get(baseURI + "/deleteAllEmployees");
        JSONObject request = new JSONObject();
        if(response.getStatusCode()==400){
            given().
                    body(request.toJSONString()).
                    when().
                    delete(baseURI + "/deleteAllEmployees").
                    then().statusCode(400).
                    body("message", hasToString("Table is Empty")).
                    log().all();
        }else{
            given().
                    body(request.toJSONString()).
                    when().
                    delete(baseURI + "/deleteAllEmployees").
                    then().statusCode(200).
                    body("message", hasToString("All Details are deleted.")).
                    log().all();
        }

    }*/
}
