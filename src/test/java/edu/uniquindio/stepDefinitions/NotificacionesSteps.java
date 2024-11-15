package edu.uniquindio.stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;

import java.util.HashMap;
import java.util.Map;

public class NotificationSteps {

    private Response response;
    private String notificationId;
    private Map<String, Object> notificationData = new HashMap<>();

    // Background: Configuración común a todos los escenarios
    @Given("el endpoint {string} está disponible")
    public void el_endpoint_esta_disponible(String url) {
        RestAssured.baseURI = url;
    }

    // Escenario 1: Enviar una notificación
    @Given("el destinatario {string} y {string} son los receptores de la notificación")
    public void el_destinatario_y_son_los_receptores_de_la_notificacion(String recipient1, String recipient2) {
        notificationData.put("recipients", new String[]{recipient1, recipient2});
    }

    @Given("el canal de notificación es {string}")
    public void el_canal_de_notificacion_es(String channel) {
        notificationData.put("channels", new String[]{channel});
    }

    @Given("el asunto de la notificación es {string}")
    public void el_asunto_de_la_notificacion_es(String subject) {
        notificationData.put("message", Map.of("subject", subject));
    }

    @Given("el cuerpo del mensaje es {string}")
    public void el_cuerpo_del_mensaje_es(String body) {
        Map<String, String> message = (Map<String, String>) notificationData.get("message");
        message.put("body", body);
    }

    @When("el usuario envía una solicitud POST a {string}")
    public void el_usuario_envia_una_solicitud_post_a(String endpoint) {
        response = RestAssured
                .given()
                .contentType("application/json")
                .body(notificationData)
                .post(endpoint);
    }

    @Then("la respuesta debe tener el código de estado {int}")
    public void la_respuesta_debe_tener_el_codigo_de_estado(int statusCode) {
        Assert.assertEquals(statusCode, response.getStatusCode());
    }

    @Then("la respuesta debe contener un mensaje de éxito")
    public void la_respuesta_debe_contener_un_mensaje_de_exito() {
        String successMessage = response.jsonPath().getString("message");
        Assert.assertTrue(successMessage.contains("success"));
    }

    // Escenario 2: Obtener todas las notificaciones
    @When("el usuario envía una solicitud GET a {string}")
    public void el_usuario_envia_una_solicitud_get_a(String endpoint) {
        response = RestAssured.given().get(endpoint);
    }

    @Then("la respuesta debe contener una lista de notificaciones")
    public void la_respuesta_debe_contener_una_lista_de_notificaciones() {
        Assert.assertTrue(response.jsonPath().getList("$").size() > 0);
    }

    @Then("cada notificación debe tener un destinatario")
    public void cada_notificacion_debe_tener_un_destinatario() {
        response.jsonPath().getList("recipients").forEach(recipient -> Assert.assertNotNull(recipient));
    }

    @Then("cada notificación debe tener un canal")
    public void cada_notificacion_debe_tener_un_canal() {
        response.jsonPath().getList("channels").forEach(channel -> Assert.assertNotNull(channel));
    }

    @Then("cada notificación debe tener un asunto")
    public void cada_notificacion_debe_tener_un_asunto() {
        response.jsonPath().getList("message.subject").forEach(subject -> Assert.assertNotNull(subject));
    }

    @Then("cada notificación debe tener un cuerpo")
    public void cada_notificacion_debe_tener_un_cuerpo() {
        response.jsonPath().getList("message.body").forEach(body -> Assert.assertNotNull(body));
    }

    // Escenario 3: Obtener una notificación por ID
    @Given("el ID de la notificación es {string}")
    public void el_id_de_la_notificacion_es(String id) {
        this.notificationId = id;
    }

    @When("el usuario envía una solicitud GET a {string}")
    public void el_usuario_envia_una_solicitud_get_a_notificacion(String endpoint) {
        response = RestAssured.given().get(endpoint + "/" + notificationId);
    }

    @Then("la respuesta debe contener los detalles de la notificación")
    public void la_respuesta_debe_contener_los_detalles_de_la_notificacion() {
        Assert.assertTrue(response.jsonPath().get("message.subject") != null);
        Assert.assertTrue(response.jsonPath().get("message.body") != null);
    }

    @Then("la notificación debe tener un destinatario")
    public void la_notificacion_debe_tener_un_destinatario() {
        Assert.assertNotNull(response.jsonPath().getString("recipients"));
    }

    @Then("la notificación debe tener un canal")
    public void la_notificacion_debe_tener_un_canal() {
        Assert.assertNotNull(response.jsonPath().getString("channels"));
    }

    @Then("la notificación debe tener un asunto")
    public void la_notificacion_debe_tener_un_asunto() {
        Assert.assertNotNull(response.jsonPath().getString("message.subject"));
    }

    @Then("la notificación debe tener un cuerpo")
    public void la_notificacion_debe_tener_un_cuerpo() {
        Assert.assertNotNull(response.jsonPath().getString("message.body"));
    }
}
