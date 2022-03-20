package restAssured;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import net.minidev.json.JSONObject;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;

public class DepartmentRestAssured {
    Logger logger = LoggerFactory.logger(DepartmentRestAssured.class);

    @BeforeTest
    public void init() {
        RestAssured.baseURI = "http://localhost:8081/department";
    }

    @Test
    public void addDepartment() {

        Response response = RestAssured.get(baseURI + "/getAllDepartment/");
        int departmentExits = response.getStatusCode();
        if (departmentExits == 204) {
            JSONObject request = new JSONObject();
            given().
                    body(request.toJSONString()).
                    when().
                    post(baseURI + "/addDepartments").
                    then().statusCode(201).body("message",
                            hasToString("Department added successfully."));

        } else {
            assertEquals(response.getStatusCode(), 200);
        }
    }

    @Test
    public void getAllDepartment() {
        when().
                get(baseURI + "/getAllDepartment/").
                then().
                statusCode(200).
                body("message", hasToString("Data Found"));
    }


    @Test
    public void getDepartmentByNameCase1() {
        String departmentName = "Java4g6";
        Response response = RestAssured.get(baseURI + "/searchDepartment/" + departmentName);
        assertEquals(response.getStatusCode(), 400);
    }

    @Test
    public void getDepartmentByNameCase2() {
        String departmentName = "Testing";
        when().
                get(baseURI + "/searchDepartment/{name}", "Testing").
                then().
                statusCode(200).
                body("departmentList.departmentid", hasItems(5),
                        "message", hasToString("Searched Department is found"),
                        "employeeDataBasedOnDepartment", empty());
    }

    @Test
    public void updateDepartmentDetailsCase1() {
        String departmentName = "Training";
        Response response = RestAssured.get(baseURI + "/searchDepartment/" + departmentName);

        if (response.getStatusCode() == 400) {
            JSONObject request = new JSONObject();
            request.put("departmentid", "1");
            request.put("departmentname", "Training");
            logger.info(request);
            given().
                    body(request.toJSONString()).contentType("application/json").
                    when().
                    put(baseURI + "/updateDepartment/1").
                    then().statusCode(202).body("message", hasToString("Department data Updated successfully."));
        } else {
            assertEquals(response.getStatusCode(), 200);
        }
    }

    @Test
    public void updateDepartmentDetailsCase2() {
        //Department exists
        JSONObject request = new JSONObject();
        request.put("departmentid", "1");
        request.put("departmentname", "Training");
        logger.info(request);
        given().
                body(request.toJSONString()).contentType("application/json").
                when().
                put(baseURI + "/updateDepartment/1").
                then().statusCode(403).body("message", hasToString("Department name exists."));
    }

    @Test
    public void updateDepartmentDetailsCase3() {
        //Searched department ID is not found
        JSONObject request = new JSONObject();
        request.put("departmentid", "1");
        request.put("departmentname", "Training");
        logger.info(request);
        given().
                body(request.toJSONString()).contentType("application/json").
                when().
                put(baseURI + "/updateDepartment/1890").
                then().statusCode(404).body("message", hasToString("Searched Department ID is not found."));
    }

    @Test
    public void deleteDepartmentCase1() {
        //delete department
        String departmentName = "Marketing";
        Response response = RestAssured.get(baseURI + "/searchDepartment/" + departmentName);
        int departmentExists = response.getStatusCode();
        if (departmentExists != 400) {
            JSONObject request = new JSONObject();
            given().
                    body(request.toJSONString()).
                    when().
                    delete(baseURI + "/deleteDepartment/7").
                    then().statusCode(200).body("message", hasToString("Department Id 7 has been deleted"));
            //log().all();
        } else {
            assertEquals(response.getStatusCode(), 400);
        }

    }

    @Test
    public void deleteDepartmentCase2() {
        //department already deleted
        JSONObject request = new JSONObject();
        given().
                body(request.toJSONString()).
                when().
                delete(baseURI + "/deleteDepartment/7").
                then().statusCode(400).
                log().all();
    }


}