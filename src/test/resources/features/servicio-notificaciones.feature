Feature: Servicio notificaciones
Quiero mandar una notificación

  Background:
    Given el endpoint "localhost:3001/notifications" está disponible

  # Enviar una notificación
  Scenario: Enviar una notificación por correo electrónico
    Given el destinatario "juand.cortest@uqvirtual.edu.co" y "mariaj.minottaz@uqvirtual.edu.co" son los receptores de la notificación
    And el canal de notificación es "Email"
    And el asunto de la notificación es "Prueba síncrona"
    And el cuerpo del mensaje es "Correo autogenerado por el servicio de notificaciones de microservicios"
    When el usuario envía una solicitud POST a "localhost:3001/notifications"
    Then la respuesta debe tener el código de estado 200
    And la respuesta debe contener un mensaje de éxito

  # Obtener todas las notificaciones
  Scenario: Obtener todas las notificaciones
    When el usuario envía una solicitud GET a "localhost:3001/notifications"
    Then la respuesta debe tener el código de estado 200
    And la respuesta debe contener una lista de notificaciones
    And cada notificación debe tener un destinatario
    And cada notificación debe tener un canal
    And cada notificación debe tener un asunto
    And cada notificación debe tener un cuerpo

  # Obtener una notificación por ID
  Scenario: Obtener una notificación específica por ID
    Given el ID de la notificación es "67327d9915a70dc170879b6c"
    When el usuario envía una solicitud GET a "localhost:3001/notifications/67327d9915a70dc170879b6c"
    Then la respuesta debe tener el código de estado 200
    And la respuesta debe contener los detalles de la notificación
    And la notificación debe tener un destinatario
    And la notificación debe tener un canal
    And la notificación debe tener un asunto
    And la notificación debe tener un cuerpo