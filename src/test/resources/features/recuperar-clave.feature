Feature: Recuperación de Contraseña
  Como un usuario que ha olvidado su contraseña
  Quiero recuperar el acceso a mi cuenta
  Para poder restablecer mi contraseña

  Background:
    Given el endpoint "/recuperacion-clave" está disponible

  # Escenario exitoso
  Scenario: Recuperación de contraseña con correo válido
    Given un correo electrónico "usuario@example.com" está registrado en el sistema
    When el usuario envía una solicitud POST a "/recuperacion-clave" con el cuerpo:
      """
      {
        "correo": "usuario@example.com"
      }
      """
    Then la respuesta debe tener el código de estado 200
    And la respuesta debe contener el mensaje "Correo de recuperación enviado con éxito."
    And la respuesta debe seguir el esquema "Exito"
