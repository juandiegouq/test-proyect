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

  # Inicio de sesión con credenciales incorrectas
  Scenario: Iniciar sesión con credenciales incorrectas
    Given un usuario con nombre de usuario "juan" y contraseña "incorrectpassword"
    When el usuario envía una solicitud POST a "/login"
    Then la respuesta debe tener el código de estado 401
    And la respuesta debe contener el mensaje "Credenciales no válidas."
    And la respuesta debe seguir el esquema "Error"

  # Inicio de sesión con datos faltantes 
  Scenario: Iniciar sesión sin nombre de usuario o contraseña
    Given un usuario con nombre de usuario "juan" y contraseña "password123"
    When el usuario envía una solicitud POST a "/login" sin su nombre de usuario o contraseña
    Then la respuesta debe tener el código de estado 400
    And la respuesta debe contener el mensaje "Nombre de usuario o contraseña faltantes."
    And la respuesta debe seguir el esquema "Error"
