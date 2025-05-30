package com.intranetdesarrollo.intranetback.controladores;

import com.intranetdesarrollo.intranetback.modelos.Mensaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class WebSocketControlador {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private SimpUserRegistry userRegistry;

//    @MessageMapping("/chat")
//    public void handleMessage(@Payload Mensaje message, Principal principal) {
//        if (principal == null) {
//            System.err.println("⚠️ Usuario no autenticado en WebSocket");
//            return;
//        }
//
//        System.out.println("🟢 Mensaje recibido de: " + principal.getName());
//        System.out.println("➡️ Reenviando a usuario con ID: " + message.getReceiverId());
//
//        userRegistry.getUsers().forEach(user -> {
//            System.out.println("Usuario conectado: " + user.getName());
//        });
//
//        messagingTemplate.convertAndSendToUser(
//                message.getReceiverId(),
//                "/queue/messages",
//                message
//        );
//    }
}
