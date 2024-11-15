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
