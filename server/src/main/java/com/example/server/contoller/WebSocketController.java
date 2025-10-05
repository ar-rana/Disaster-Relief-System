package com.example.server.contoller;

import java.util.List;

import com.example.server.model.DTOs.ProviderInfo;
import com.example.server.model.enums.Keys;
import com.example.server.service.redis.RedisCacheService;
import com.example.server.service.redis.RedisListService;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@Slf4j
public class WebSocketController {

    @Autowired
    private RedisCacheService cache;

    @Autowired
    private RedisListService lists;

    @MessageMapping("/reliefdata/{reqId}")
    public ResponseEntity<?> providerReq(@RequestBody ProviderInfo info, @PathVariable String reqId) {
        String key = Keys.key(Keys.ASSIGNEE, reqId);
        lists.insertInList(key, info, ProviderInfo.class);
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
