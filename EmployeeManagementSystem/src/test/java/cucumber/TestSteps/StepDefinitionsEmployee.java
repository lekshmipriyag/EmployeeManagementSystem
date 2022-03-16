package cucumber.TestSteps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;

import static org.testng.Assert.assertEquals;

public class StepDefinitionsEmployee {

    Logger logger = LoggerFactory.logger(StepDefinitionsEmployee.class);

    private static final String BASE_URL = "http://localhost:8081/employee";
    RequestSpecification request;
    private static Response response;
    private static String jsonString;
    private static int statusCode;

    //Add predefined departments
    @Given("A list of predefined departments")
    public void a_list_of_predefined_departments() {
        request = RestAssured.given();
    }

    @When("No department exists")
    public void no_department_exists() {
        Response result = request.get("http://localhost:8081/department/getAllDepartment/");
        statusCode = result.getStatusCode();
    }

    @Then("add new department with success response")
    public void add_new_department_with_success_response() {
        logger.info("Status Code " + statusCode);
        if (statusCode == 204) {
            response = request.post("http://localhost:8081/department/addDepartments");
            assertEquals(response.getStatusCode(), 201);
        }
    }

    //Add an Employee
    @Given("Employee details")
    public void employee_details() {
        RestAssured.baseURI = BASE_URL;
        request = RestAssured.given();
        request.header("Content-Type", "application/json");
    }

    @When("No employee exists with the employee number")
    public void no_employee_exists_with_the_employee_number() {
        Response result = request.get("/searchEmployeeNumber/EMP400");
        statusCode = result.getStatusCode();
    }

    @Then("add this new employee to the department")
    public void add_this_new_employee_to_the_department() {
        if (statusCode == 404) {
            response = request.body("{\n" +
                            "        \n" +
                            "            \"name\": \"Jeni\",\n" +
                            "            \"employeeNumber\": \"EMP400\",\n" +
                            "            \"dob\": \"17/01/1997\",\n" +
                            "            \"gender\": \"Male\",\n" +
                            "            \"emailID\": \"user8@gmail.com\",\n" +
                            "            \"mobile\": \"0431440140\",\n" +
                            "            \"qualification\": \"MCA\",\n" +
                            "            \"experience\":  6,\n" +
                            "            \"joiningDate\": \"12/03/2022\",\n" +
                            "            \"city\": \"Melbourne\",\n" +
                            "            \"state\": \"Victoria\",\n" +
                            "            \"department\": {\n" +
                            "                \"departmentid\":1\n" +
                            "            }\n" +
                            "        }")
                    .post("/addEmployee");
            assertEquals(response.getStatusCode(), 201);
        }
    }

    // Get all Employee Details
    @When("I try to fetch all employee details")
    public void i_try_to_fetch_all_employee_details() {
        request = RestAssured.given();
        response = request.get("/getAllEmployee/");
        statusCode = response.getStatusCode();
    }

    @Then("returns a valid response")
    public void returns_a_valid_response() {
        assertEquals(response.getStatusCode(), 200);
    }

    // Get Employee by ID
    @When("I try to fetch an employee data based on ID")
    public void i_try_to_fetch_an_employee_data_based_on_ID() {
        request = RestAssured.given();
        response = request.get("/getEmployee/200");
        statusCode = response.getStatusCode();
    }

    @Then("returns a valid response_employeeID")
    public void returns_a_valid_response_employeeID() {
        assertEquals(response.getStatusCode(), 404);
    }

    // Get Employee by Name
    @When("I try to fetch an employee data based on Name")
    public void i_try_to_fetch_an_employee_data_based_on_Name() {
        request = RestAssured.given();
        response = request.get("/searchName/Jeni");
        statusCode = response.getStatusCode();
    }

    @Then("returns a valid response_employeeName")
    public void returns_a_valid_response_employeeName() {
        assertEquals(response.getStatusCode(), 302);
    }

    // Employee not found
    @When("I try to fetch an employee who is not registered")
    public void i_try_to_fetch_an_employee_who_is_not_registered() {
        request = RestAssured.given();
        response = request.get("/searchName/Lekshmi");
        statusCode = response.getStatusCode();
    }

    @Then("returns a not found response")
    public void returns_a_not_found_response() {
        assertEquals(response.getStatusCode(), 404);
    }

    // Delete employee by ID
    @When("I delete an employee")
    public void i_delete_an_employee() {
        request = RestAssured.given();
        response = request.get("/getEmployee/1");
        statusCode = response.getStatusCode();
    }

    @Then("return a success message")
    public void return_a_success_message() {
        assertEquals(response.getStatusCode(), 200);
    }

}