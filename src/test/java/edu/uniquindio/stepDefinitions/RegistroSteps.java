package edu.uniquindio.stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.cucumber.java.en.Then;
import static io.restassured.RestAssured.*;

public class RegistroSteps {

    private String baseUrl = "http://localhost:8080"; // Cambia según sea necesario
    private int idUsuario;
    private String nombreUsuario;
    private String correoUsuario;
    private String contraseñaUsuario;
    private String response;
    private String token;

    /**
     * Endpoint disponible
     * 
     * @param endpoint
     */

    @Given("el endpoint {string} está disponible")
    public void elEndpointEstaDisponible(String endpoint) {
        // Verificar si el endpoint está disponible
       // Response response = post(baseUrl + endpoint);
       // int statusCode = response.getStatusCode();
        // Verificar si el código de estado es uno de los aceptables
       // if (statusCode == 201 || statusCode == 409 || statusCode == 400 || statusCode == 403) {
        //    System.out.println("El endpoint está disponible. Código de estado: " + statusCode);
        //} else {
            //System.out.println("El endpoint no está disponible. Código de estado: " + statusCode);
        //}
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
        this.idUsuario = 1;
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
        String requestBody = String.format(
                "{\"nombre_usuario\": \"%s\", \"correo\": \"%s\", \"contraseña\": \"%s\"}",
                nombre, correo, contraseñaUsuario);

        // Realizar la solicitud POST para registrar el usuario
        Response response = given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post(baseUrl + "/usuarios");

        // Verificar la respuesta
        if (response.statusCode() == 201) {

            String usuarioId = response.jsonPath().getString("usuarioId");

            if (usuarioId != null) {
                System.out.println("Nuevo usuario registrado con ID: " + usuarioId);
            } else {
                System.out.println("No se encontró el campo 'usuarioId' en la respuesta.");
            }
        } else {
            System.out.println(
                    "Error en la solicitud de registro: " + response.statusCode() + " " + response.body().asString());
        }
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
                .body("{\"nombre\": \"" + nombreUsuario + "\", \"correo\": \"" + correoUsuario
                        + "\", \"contraseña\": \"" + contraseñaUsuario + "\"}")
                .when()
                .post(baseUrl + endpoint)
                .asString();
    }

    /**
     * Verificar codigo de éstado
     * 
     * @param statusCode
     */

    @Then("la respuesta debe tener el código de estado {int}")
    public void laRespuestaDebeTenerCodigoEstado(int statusCode) {
        // Validar el código de estado de la respuesta
        given().when().post(baseUrl + "/usuarios") // Asegúrate de usar la misma lógica que en el When
                .then().statusCode(statusCode);
    }

    /**
     * La respuesta debe tener el mensaje especificado
     * 
     * @param mensajeEsperado
     */

    @Then("la respuesta debe contener el mensaje {string}")
    public void laRespuestaDebeContenerMensaje(String mensajeEsperado) {
        assert response.contains(mensajeEsperado);
    }

    @Then("la respuesta debe seguir el esquema {string}")
    public void laRespuestaDebeSeguirEsquema(String esquema) {

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
