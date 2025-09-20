package com.example.server.service.rabbit;

import com.example.server.config.RabbitConfig;
import com.example.server.model.ReliefReq;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Return;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Consumer {

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

        // Assignment to be DONE
        System.out.println("DO-STUFF HERE");
    }
}
