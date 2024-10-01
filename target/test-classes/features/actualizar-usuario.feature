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

  # Actualizar los detalles sin un token
  Scenario: Actualizar detalles sin estar autenticado
    Given un usuario con ID "123" existe
    And el usuario no está autenticado
    And los nuevos datos del usuario son:
      | nombre_usuario  | correo               | contraseña |
      | nuevo_nombre    | nuevo@correo.com     | nueva_contraseña |
    When el usuario envía una solicitud PUT a "/usuarios/123" con los siguientes datos:
      | nombre_usuario  | correo               | contraseña |
      | nuevo_nombre    | nuevo@correo.com     | nueva_contraseña |
    Then la respuesta debe tener el código de estado 401
    And la respuesta debe contener el mensaje "No autorizado."
    And la respuesta debe seguir el esquema "Error"

  # Actualizar los detalles de otro usuario
  Scenario: Actualizar detalles sin especificar ningún dato nuevo
    Given un usuario con ID "123" existe
    And el usuario está autenticado con un token válido
    And los nuevos datos del usuario son:
      | nombre_usuario  | correo               | contraseña |
      | nuevo_nombre    | nuevo@correo.com     | nueva_contraseña |
    When el usuario envía una solicitud PUT a "/usuarios/123" sin ninguno de los 3 datos nuevos
    Then la respuesta debe tener el código de estado 400
    And la respuesta debe contener el mensaje "Datos faltantes."
    And la respuesta debe seguir el esquema "Error"
