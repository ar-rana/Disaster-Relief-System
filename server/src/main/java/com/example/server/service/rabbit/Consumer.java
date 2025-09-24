package com.example.server.service.rabbit;

import com.example.server.config.RabbitConfig;
import com.example.server.model.ReliefReq;
import com.example.server.model.User;
import com.example.server.model.enums.Keys;
import com.example.server.service.ProviderService;
import com.example.server.service.UserService;
import com.example.server.service.redis.RedisCacheService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Return;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Consumer {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisCacheService cache;

    @Autowired
    private ProviderService providerService;

    private final SimpMessagingTemplate messagingTemplate;
    public Consumer(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @RabbitListener(queues = RabbitConfig.QUEUE_NAME)
    public void listen(String msg) {
        log.info("[R_MQ-CONSUMER] Received DATA from PQ: {}", msg);
        ObjectMapper o = new ObjectMapper();
        ReliefReq rq = null;
        try {
            rq = o.readValue((String) msg, ReliefReq.class);
        } catch (Exception ex) {
            log.error("[R_MQ-CONSUMER] Failed to PARSE Relief OBJECT: {}", ex.getMessage());
            return;
        }

        log.info("[R_MQ-CONSUMER] PROCESSING: {}", rq);
        User provider = userService.getRandomProvider();
        String wsUid = cache.getCache(Keys.key(Keys.USER, "wsuid/" + provider.getUsername()), String.class);

        providerService.assignRelief(rq, provider.getUsername());

        messagingTemplate.convertAndSend("/navigation/" + wsUid, rq);
        log.info("[R_MQ-CONSUMER] Req: {} assigned to: {}", rq.getUid(), provider.getUsername());
    }
}
