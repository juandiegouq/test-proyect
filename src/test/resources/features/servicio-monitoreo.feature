Feature: Servicio monitoreo
Quiero registrar o consultar la salud de un microservicio

  Background:
    Given el endpoint ":3000/register" está disponible

  # Registrar microservicio de crud de usuarios
  Scenario: Registrar un microservicio
    Given "localhost:3000/register" está disponible
    And el microservicio "user_crud_service" con el endpoint "http://user-crud-service:8080", la frecuencia 60 y los emails juand.cortest@uqvirtual.edu.co y mariaj.minottaz@uqvirtual.edu.co 
    When el usuario envía una solicitud POST a "localhost:3000/register"
    Then la respuesta debe tener el código de estado 200

  # Registrar microservicio de logs
  Scenario: Registrar un microservicio
    Given "localhost:3000/register" está disponible
    And el microservicio "logs_service" con el endpoint "http://logs-service:8000", la frecuencia 60 y los emails juand.cortest@uqvirtual.edu.co y mariaj.minottaz@uqvirtual.edu.co 
    When el usuario envía una solicitud POST a "localhost:3000/register"
    Then la respuesta debe tener el código de estado 200

  # Registrar microservicio de notificaciones
  Scenario: Registrar un microservicio
    Given "localhost:3000/register" está disponible
    And el microservicio "logs_service" con el endpoint "http://notification-service:3001", la frecuencia 60 y los emails juand.cortest@uqvirtual.edu.co y mariaj.minottaz@uqvirtual.edu.co 
    When el usuario envía una solicitud POST a "localhost:3000/register"
    Then la respuesta debe tener el código de estado 200

  # Consultar salud de todos los microservicios 
  Scenario: Registrar un microservicio
    Given "localhost:3000/health" está disponible
    When el usuario envía una solicitud GET a "localhost:3000/health"
    Then la respuesta debe tener el código de estado 200

  # Consultar salud del microservicio de crud de usuarios
  Scenario: Registrar un microservicio
    Given "localhost:8080/health/user_crud_service" está disponible
    When el usuario envía una solicitud GET a "localhost:8080/health/user_crud_service"
    Then la respuesta debe tener el código de estado 200

  # Consultar salud del microservicio de logs
  Scenario: Registrar un microservicio
    Given "localhost:8000/health/logs_service" está disponible
    When el usuario envía una solicitud GET a "localhost:8000/health/logs_service"
    Then la respuesta debe tener el código de estado 200

  # Consultar salud del microservicio de notificaciones
  Scenario: Registrar un microservicio
    Given "localhost:3001/health/notification_service" está disponible
    When el usuario envía una solicitud GET a "localhost:3001/health/notification_service"
    Then la respuesta debe tener el código de estado 200