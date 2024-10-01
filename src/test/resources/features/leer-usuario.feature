Feature: Obtener detalles de usuario
  Como un usuario quiero obtener mis detalles de usuario 

  Background:
    Given el endpoint "/usuarios/{usuarioId}" está disponible

  # Obtener detalles de usuario con token valido
  Scenario: Obtener detalles de mi usuario 
    Given un usuario con ID "123" existe
    And el usuario está autenticado con un token válido
    When el usuario envía una solicitud GET a "/usuarios/123"
    Then la respuesta debe tener el código de estado 200
    And la respuesta debe contener los detalles del usuario
    And la respuesta debe seguir el esquema "Usuario"

  # Obtener detalles de otro usuario 
  Scenario: Obtener detalles de otro usuario
    Given un usuario con ID "123" existe
    And el usuario está autenticado con un token valido
    And el usaurio con ID "12" existe
    When el usuario envía una solicitud GET a "/usuarios/12"
    Then la respuesta debe tener el código de estado 401
    And la respuesta debe contener el mensaje "No autorizado."
    And la respuesta debe seguir el esquema "Error"

  # Obtener detalles de otro usuario, el cual no existe
  Scenario: Obtener detalles de otro usuario, el cual no existe
    Given un usuario con ID "123" existe
    And el usuario está autenticado con un token válido
    And el usuario con ID "12" no existe
    When el usuario envía una solicitud GET a "/usuarios/12"
    Then la respuesta debe tener el código de estado 404
    And la respuesta debe contener el mensaje "Usuario no encontrado."
    And la respuesta debe seguir el esquema "Error"


