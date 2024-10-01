Feature: Registro de Usuario
  Como un usuario
  Quiero registrarme en la aplicación


  Background:
    Given el endpoint "/usuarios" está disponible


  # Todos los datos, registro exitoso . 
  Scenario: Registrar un usuario nuevo con datos válidos
    Given un usuario con nombre "juan" y correo "juand.cortest@uqvirtual.edu.co" y contraseña "juancontra"
    And el usuario con nombre "juan" y correo "juand.cortest@uqvirtual.edu.co" no esta previamente registrado
    When el usuario envía una solicitud POST a "/usuarios"
    Then la respuesta debe tener el código de estado 201
    And la respuesta debe contener el mensaje "Usuario registrado con éxito"
    And la respuesta debe seguir el esquema "RegistroExitoso"


  # Escenario con correo o usuario ya registrado .
  Scenario: Registrar un usuario con un correo o usuario que ya está registrado
    Given un usuario con nombre "juan" y correo "juand.cortest@uqvirtual.edu.co" y contraseña "juancontra"
    And ya existe un usuario con nombre "juan" y correo "juand.cortest@uqvirtual.edu.co"
    When el usuario envía una solicitud POST a "/usuarios"
    Then la respuesta debe tener el código de estado 409
    And la respuesta debe contener el mensaje "El usuario ya existe"
    And la respuesta debe seguir el esquema "Error"


  # Escenario sin nombre de usuario, correo o contraseña
  Scenario: Registrar un usuario con un nombre de usuario, correo o contraseña faltante
    Given un usuario con nombre "maria" y correo "mariam@uqvirtual.edu.co" y contraseña "mariacontra"
    When el usuario envía una solicitud POST a "/usuarios" sin alguno de los 3 datos
    Then la respuesta debe tener el código de estado 400
    And la respuesta debe contener el mensaje "Entrada inválida, falta información."
    And la respuesta debe seguir el esquema "Error"