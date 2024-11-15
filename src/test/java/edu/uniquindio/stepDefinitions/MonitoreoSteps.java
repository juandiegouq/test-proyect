package edu.uniquindio.stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;

import java.util.HashMap;
import java.util.Map;

public class MonitoringSteps {

    private Response response;
    private String nombreMicroservicio;
    private String endpointMicroservicio;
    private int frecuencia;
    private String[] correos;

    // Registrar microservicio de crud de usuarios
    @Given("el endpoint {string} está disponible")
    public void el_endpoint_está_disponible(String endpoint) {
        RestAssured.baseURI = "http://" + endpoint;
    }

    @Given("el microservicio {string} con el endpoint {string}, la frecuencia {int} y los emails {string} y {string}")
    public void el_microservicio_con_el_endpoint(String nombreServicio, String endpointServicio, int frecuencia, String correo1, String correo2) {
        this.nombreMicroservicio = nombreServicio;
        this.endpointMicroservicio = endpointServicio;
        this.frecuencia = frecuencia;
        this.correos = new String[] { correo1, correo2 };
    }

    @When("el usuario envía una solicitud POST a {string}")
    public void el_usuario_envía_una_solicitud_post_a(String endpoint) {
        Map<String, Object> cuerpoSolicitud = new HashMap<>();
        cuerpoSolicitud.put("nombreServicio", this.nombreMicroservicio);
        cuerpoSolicitud.put("endpointServicio", this.endpointMicroservicio);
        cuerpoSolicitud.put("frecuencia", this.frecuencia);
        cuerpoSolicitud.put("correos", this.correos);

        response = RestAssured
                .given()
                .contentType("application/json")
                .body(cuerpoSolicitud)
                .post(endpoint);
    }

    @When("el usuario envía una solicitud GET a {string}")
    public void el_usuario_envía_una_solicitud_get_a(String endpoint) {
        response = RestAssured
                .given()
                .contentType("application/json")
                .get(endpoint);
    }

    @Then("la respuesta debe tener el código de estado {int}")
    public void la_respuesta_debe_tener_el_codigo_de_estado(Integer codigoEstado) {
        Assert.assertEquals(codigoEstado.intValue(), response.getStatusCode());
    }
}
