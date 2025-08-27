package com.example.server.contoller;

import com.example.server.model.ReliefReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@Slf4j
public class WebSocketController {

    @MessageMapping("/reliefdata/{providerId}")
    @SendTo("/navigation/{providerId}")
    public ResponseEntity<ReliefReq> whiteboardDraw() {
        return ResponseEntity.ok().build();
    }

//    @MessageMapping("/reliefdata/")
    @SendTo("/navigation/**")
    public ResponseEntity<ReliefReq> whiteboardDraw(@RequestBody ReliefReq item) {
        return ResponseEntity.ok(item);
    }
}
