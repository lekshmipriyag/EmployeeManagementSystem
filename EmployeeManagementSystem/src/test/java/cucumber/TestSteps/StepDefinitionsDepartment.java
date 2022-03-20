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

public class StepDefinitionsDepartment {

    Logger logger = LoggerFactory.logger(StepDefinitionsEmployee.class);

    private static final String BASE_URL = "http://localhost:8081/department";
    RequestSpecification request;
    private static Response response;
    private static String jsonString;
    private static int statusCode;

    //Add predefined departments
    @Given("A list of departments")
    public void a_list_of_departments() {
        request = RestAssured.given();
    }

    @When("No departments")
    public void no_departments() {
        //  RestAssured.baseURI = BASE_URL;
        Response result = request.get(BASE_URL + "/getAllDepartment/");
        statusCode = result.getStatusCode();
    }

    @Then("add the listed departments with success response")
    public void add_the_listed_departments_with_success_response() {
        RestAssured.baseURI = BASE_URL;
        if (statusCode == 204) {
            response = request.post(BASE_URL + "/addDepartments");
            assertEquals(response.getStatusCode(), 201);
        }
    }

    // Get all departments
    @When("I try to get all departments")
    public void i_try_to_get_all_departments() {
        request = RestAssured.given();
        response = request.get(BASE_URL + "/getAllDepartment/");
    }

    @Then("it returns success message if a department exists")
    public void it_returns_success_message_if_a_department_exists() {
        assertEquals(response.getStatusCode(), 200);
    }

    //Try to fetch a department which is not exists
    @When("I try to search a department which is not exists")
    public void i_try_to_search_a_department_which_is_not_found() {
        request = RestAssured.given();
        response = request.get(BASE_URL + "/searchDepartment/Gaming");
    }

    @Then("it returns a bad request status")
    public void it_returns_a_bad_request_status() {
        assertEquals(response.getStatusCode(), 400);
    }

    //search an existing department
    @When("I try to search an existing department")
    public void i_try_to_search_an_existing_department() {
        request = RestAssured.given();
        response = request.get(BASE_URL + "/searchDepartment/Testing");
    }

    @Then("it returns the success status")
    public void it_returns_the_success_status() {
        assertEquals(response.getStatusCode(), 200);
    }

    //update a department with new department name
    @Given("A department data")
    public void a_department_data() {
        request = RestAssured.given().body("{\n" +
                "                 \"departmentid\":1,\n" +
                "                \"departmentname\": \"Training\"\n" +
                "               \n" +
                " }");
    }

    @When("I try to search and update the department")
    public void i_try_to_search_and_update_the_department() {
        Response departmentExists = request.get(BASE_URL + "/searchDepartment/Training");
        statusCode = departmentExists.getStatusCode();
    }

    @Then("department would update and return a success status")
    public void department_would_update_and_return_a_success_status() {
        if (statusCode == 400) {
            request.header("Content-Type", "application/json");
            response = request.put("updateDepartment/1");
            assertEquals(response.getStatusCode(), 202);
        }
    }

    //update a department with existing department name
    @Given("A department details")
    public void a_department_details() {
        request = RestAssured.given().body("{\n" +
                "                 \"departmentid\":1,\n" +
                "                \"departmentname\": \"Finance\"\n" +
                "               \n" +
                " }");
    }

    @When("I try to update the departmentname with existing departmentname")
    public void i_try_to_update_the_departmentname_with_existing_departmentname() {
        request.header("Content-Type", "application/json");
        response = request.put("updateDepartment/1");
    }

    @Then("department would reject the request")
    public void department_would_reject_the_request() {
        assertEquals(response.getStatusCode(), 403);
    }

    //update a department with unknown Department ID
    @Given("A department information")
    public void a_department_information() {
        request = RestAssured.given().body("{\n" +
                "                 \"departmentid\":1,\n" +
                "                \"departmentname\": \"Finance\"\n" +
                "               \n" +
                " }");
    }

    @When("I try to update a department with an unknown ID")
    public void i_try_to_update_a_department_with_an_unknown_ID() {
        request.header("Content-Type", "application/json");
        response = request.put("updateDepartment/11233");
    }

    @Then("department would forbid the request")
    public void department_would_forbid_the_request() {
        assertEquals(response.getStatusCode(), 404);
    }
    // Delete employee by ID
    @When("I try to delete an existing department")
    public void i_try_to_delete_an_existing_department() {
        request = RestAssured.given();
        Response departmentExists = request.get(BASE_URL + "/searchDepartment/Marketing");
        statusCode = departmentExists.getStatusCode();
    }

    @Then("department has been deleted with success status")
    public void department_has_been_deleted_with_success_status() {
        if (statusCode != 400) {
            response = request.delete("/deleteDepartment/7");
            assertEquals(response.getStatusCode(), 200);
        }
    }

    // Delete an unknown department
    @When("I try to delete an unknown department")
    public void i_try_to_delete_an_unknown_department() {
        request = RestAssured.given();
        response = request.delete("/deleteDepartment/7");
    }

    @Then("I receive a forbid response")
    public void i_receive_a_forbid_response() {
            assertEquals(response.getStatusCode(), 400);
    }
}
