Feature: Inicio de sesión del Usuario
  Como un usuario registrado
  Quiero iniciar sesión en la aplicación
  Para poder acceder a mis datos y funcionalidades

  Background:
    Given el endpoint "/login" está disponible

  # Inicio de sesión con credenciales validas
  Scenario: Iniciar sesión con credenciales válidas
    Given un usuario con nombre de usuario "juan" y contraseña "password123"
    When el usuario envía una solicitud POST a "/login"
    Then la respuesta debe tener el código de estado 200
    And la respuesta debe contener un token JWT
    And la respuesta debe seguir el esquema "Token"
