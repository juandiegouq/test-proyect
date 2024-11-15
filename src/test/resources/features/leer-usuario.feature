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
