package com.intranetdesarrollo.intranetback.controladores;

import com.intranetdesarrollo.intranetback.modelos.Mensaje;
import com.intranetdesarrollo.intranetback.modelos.Tablero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatControlador {

    @MessageMapping("/chat/{username}")
    @SendTo("/chat/{username}")
    public Mensaje sendMessage(@DestinationVariable String username, Mensaje message) {
        System.out.println("📨 Enviando mensaje a usuario: " + username);
        // Aquí podrías agregar lógica para validar o guardar mensajes
        return message; // Se enviará a todos suscriptores del canal /chat/{username}
    }

    @MessageMapping("/publicChat")
    @SendTo("/topic/publicChat")
    public Mensaje sendPublicMessage(Mensaje message) {
        return message;
    }

    @MessageMapping("/game/{username}")
    @SendTo("/game/{username}")
    public Tablero sendTablero(@DestinationVariable String username, Tablero tablero) {
        System.out.println("📨 Enviando RETO a usuario: " + username);
        // Aquí podrías agregar lógica para validar o guardar mensajes
        return tablero;
    }
}