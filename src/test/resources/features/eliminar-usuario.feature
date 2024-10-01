Feature: Eliminar un Usuario
  Como un usuario 
  Quiero eliminar mi cuenta de usuario
  Para que mi información sea eliminada del sistema

  Background:
    Given el endpoint "/usuarios/{usuarioId}" está disponible

  # Eliminar con un token valido
  Scenario: Eliminar mi usuario con un token valido
    Given un usuario con ID "123" existe
    And el usuario está autenticado con un token válido
    When el usuario envía una solicitud DELETE a "/usuarios/123"
    Then la respuesta debe tener el código de estado 200
    And la respuesta debe contener el mensaje "Usuario eliminado con éxito."
    And la respuesta debe seguir el esquema "Exito"

  # Eliminar sin un token valido
  Scenario: Eliminar mi usuario con un token no valido
    Given un usuario con ID "123" existe
    And el usuario no está autenticado con un token válido
    When el usuario envía una solicitud DELETE a "/usuarios/123"
    Then la respuesta debe tener el código de estado 401
    And la respuesta debe contener el mensaje "No autorizado."
    And la respuesta debe seguir el esquema "Error"

  # Eliminar otro usuario
  Scenario: Eliminar otro usuario con un token valido
    Given un usuario con ID "123" existe 
    And el usuario está autenticado con un token válido
    And un usuario con ID "12" existe 
    When el usuario envía una solicitud DELETE a "/usuarios/12"
    Then la respuesta debe tener el código de estado 401
    And la respuesta debe contener el mensaje "No autorizado."
    And la respuesta debe seguir el esquema "Error"

  # Eliminar otro usuario que no existe
  Scenario: Eliminar otro usuario no existente con un token valido
    Given un usuario con ID "123" existe 
    And el usuario está autenticado con un token válido
    And un usuario con ID "12" no existe 
    When el usuario envía una solicitud DELETE a "/usuarios/12"
    Then la respuesta debe tener el código de estado 404
    And la respuesta debe contener el mensaje "Usuario no encontrado."
    And la respuesta debe seguir el esquema "Error" 
