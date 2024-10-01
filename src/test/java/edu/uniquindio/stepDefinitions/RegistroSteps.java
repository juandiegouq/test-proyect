package edu.uniquindio.stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.cucumber.java.en.Then;
import static io.restassured.RestAssured.*;

public class RegistroSteps {

    private String baseUrl = "http://localhost:8080"; // Cambia según sea necesario
    private int idUsuario = 72;
    private String nombreUsuario;
    private String correoUsuario;
    private String contraseñaUsuario;
    private Response response;
    private String token;
    private String responseBody;

    /**
     * Endpoint disponible
     * 
     * @param endpoint
     */

    @Given("el endpoint {string} está disponible")
    public void elEndpointEstaDisponible(String endpoint) {
      //Si esta
    }

    /**
     * Datos del usuario
     * 
     * @param nombre
     * @param correo
     * @param contraseña
     */

    @Given("un usuario con nombre {string} y correo {string} y contraseña {string}")
    public void unUsuarioConDatos(String nombre, String correo, String contraseña) {
        this.nombreUsuario = nombre;
        this.correoUsuario = correo;
        this.contraseñaUsuario = contraseña;
    }

    /**
     * Asegurar que el usuario no esta registrado
     * @param nombre
     * @param correo
     */

    @Given("el usuario con nombre {string} y correo {string} no esta previamente registrado")
    public void elUsuarioNoEstaRegistrado(String nombre, String correo) {
        // Token
        token = realizarLogin(nombre, contraseñaUsuario);

        // Si existe el token
        if (token != null) {
            // Intentar eliminar el usuario existente
            eliminarUsuario(token, idUsuario);
        } else {
            throw new RuntimeException("No se recibió un token en la respuesta de login.");
        }

    }

    /**
     * Asegurar que el usuario ya exista
     * 
     * @param nombre
     * @param correo
     */

    @Given("ya existe un usuario con nombre {string} y correo {string}")
    public void yaExisteUnUsuario(String nombre, String correo) {

        // Crear el objeto JSON para registrar al usuario
    }

    /**
     * Usuario envia una solicitud post con el cuerpo usuario, correo, contraseña
     * 
     * @param endpoint
     */

    @When("el usuario envía una solicitud POST a {string}")
    public void elUsuarioEnvíaSolicitudPOST(String endpoint) {
        // Enviar solicitud POST
        response = given()
                .contentType("application/json")
                .body("{\"nombre_usuario\": \"" + nombreUsuario + "\", \"correo\": \"" + correoUsuario
                        + "\", \"contraseña\": \"" + contraseñaUsuario + "\"}")
                .when()
                .post(baseUrl + endpoint);
        if(response.statusCode()==201){
            idUsuario++;
        }
    }

    @When("el usuario envía una solicitud POST a {string} sin alguno de los 3 datos")
    public void elUsuarioEnvíaSolicitudSinDatosPOST(String endpoint) {

        // Enviar solicitud POST
        response = given()
                .contentType("application/json")
                .body("{\"nombre_usuario\": \"" + nombreUsuario + "\", \"correo\": \"" + correoUsuario + "\"}")
                .when()
                .post(baseUrl + endpoint);
    }

    /**
     * Verificar codigo de éstado
     * 
     * @param statusCode
     */

    @Then("la respuesta debe tener el código de estado {int}")
    public void laRespuestaDebeTenerCodigoEstado(int statusCode) {
        // Validar el código de estado de la respuesta
        response.then().statusCode(statusCode);
    }

    /**
     * La respuesta debe tener el mensaje especificado
     * 
     * @param mensajeEsperado
     */

    @Then("la respuesta debe contener el mensaje {string}")
    public void laRespuestaDebeContenerMensaje(String mensajeEsperado) {
        JsonPath jsonResponse = response.jsonPath();
        String valor = jsonResponse.getString("message");
        assert valor.equals(mensajeEsperado);
    }

    @Then("la respuesta debe seguir el esquema {string}")
    public void laRespuestaDebeSeguirEsquema(String esquema) {
        //JSON SCHEMA
    }

    /**
     * Método para realizar login
     * 
     * @param username
     * @param password
     * @return token o no
     */

    private String realizarLogin(String username, String password) {
        Response response = given()
                .contentType("application/json")
                .body("{\"nombre_usuario\": \"" + username + "\", \"contraseña\": \"" + password + "\"}")
                .when()
                .post(baseUrl + "/login");

        // Verificar si la solicitud fue exitosa
        if (response.statusCode() == 200) {
            return response.jsonPath().getString("token");
        } else {
            System.out.println(
                    "Error en la solicitud de login: " + response.statusCode() + " " + response.body().asString());
            return null;
        }
    }

    /**
     * Método para eliminar usuario
     * 
     * @param token
     * @param nombre
     */

    private void eliminarUsuario(String token, int idUsuario) {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .contentType("application/json")
                .when()
                .delete(baseUrl + "/usuarios/" + idUsuario); // Cambia esto según tu API

        if (response.statusCode() == 200) {
            System.out.println("Usuario eliminado con éxito: " + response.statusCode());
        } else {
            System.out.println(
                    "Error al eliminar el usuario: " + response.statusCode() + " " + response.body().asString());
        }
    }

}
