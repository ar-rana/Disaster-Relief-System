package com.example.server.contoller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@Slf4j
public class WebSocketController {

    @MessageMapping("/reliefdata/{providerId}")
    public ResponseEntity<?> providerReq() {
        return ResponseEntity.ok().build();
    }

    @SendTo("/navigation/{providerId}")
    public ResponseEntity<?> sendToProvider(@PathVariable String providerId) {
        return ResponseEntity.ok().build();
    }

    @SendTo("/navigation")
    public ResponseEntity<?> navigation() {
        return ResponseEntity.ok().build();
    }
}
