package edu.uniquindio.stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;
import org.hamcrest.Matchers;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;


import java.util.HashMap;
import java.util.Map;

public class LoginSteps {

    Faker faker = new Faker();
    private Response response;
    private String nombreUsuario;
    private String correoUsuario;
    private String contraseñaUsuario;
    private Response response;
    private String token;
    private String responseBody;

    @Given("the endpoint \"/login\" is available")
    public void the_endpoint_login_is_available() {
        RestAssured.baseURI = "http://localhost:8080"; // Adjust the URL as per your configuration
    }

    @Given("a user with username {string} and password {string}")
    public void a_user_with_username_and_password(String username, String password) {
        this.nombreUsuario = faker.name().fullName();
        this.correoUsuario = faker.internet().emailAddress();
        this.contraseñaUsuario = faker.internet().password(8, 12);



    }

    @When("the user sends a POST request to \"/login\"")
    public void the_user_sends_a_post_request_to_login() {
        Map<String, String> credentials = new HashMap<>();
        credentials.put("username", "juan");
        credentials.put("password", "password123");

        response = RestAssured
                .given()
                .contentType("application/json")
                .body(credentials)
                .post("/login");
    }

    @Then("the response should have status code {int}")
    public void the_response_should_have_status_code(Integer statusCode) {
        Assert.assertEquals(statusCode.intValue(), response.getStatusCode());
    }

    @Then("the response should contain a JWT token")
    public void the_response_should_contain_a_jwt_token() {
        String token = response.jsonPath().getString("token");
        Assert.assertNotNull("JWT token should not be null", token);
        Assert.assertTrue("JWT token should be a non-empty string", token.length() > 0);
    }

    @Then("the response should follow the {string} schema")
    public void the_response_should_follow_the_schema(String schema) {
        // Implement validation for response schema
        response.then().body("token", Matchers.notNullValue());
    }
}
