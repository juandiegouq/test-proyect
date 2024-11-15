Feature: Actualizar Detalles del Usuario
  Como un usuario quiero actualizar los detalles de mi usuario
  Para mantener mi información actualizada en el sistema

  Background:
    Given el endpoint "/usuarios/{usuarioId}" está disponible

  # Actualizar los detalles con un token valido
  Scenario: Actualizar detalles de un usuario autenticado
    Given un usuario con ID "123" existe
    And el usuario está autenticado con un token válido
    And los nuevos datos del usuario son:
      | nombre_usuario  | correo               | contraseña |
      | nuevo_nombre    | nuevo@correo.com     | nueva_contraseña |
    When el usuario envía una solicitud PUT a "/usuarios/123" con los siguientes datos:
      | nombre_usuario  | correo               | contraseña |
      | nuevo_nombre    | nuevo@correo.com     | nueva_contraseña |
    Then la respuesta debe tener el código de estado 200
    And la respuesta debe contener el mensaje "Usuario actualizado con éxito."
    And la respuesta debe seguir el esquema "Exito"
