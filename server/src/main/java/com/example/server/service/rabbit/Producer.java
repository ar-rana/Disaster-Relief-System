package com.example.server.service.rabbit;

import com.example.server.config.RabbitConfig;
import com.example.server.model.ReliefReq;
import com.example.server.model.enums.Criticality;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.example.server.model.enums.Criticality.HIGH;
import static com.example.server.model.enums.Criticality.VERY_HIGH;

@Component
@Slf4j
public class Producer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessage(ReliefReq obj, Criticality pq) {
        int priority = getPriority(pq);
        try {
            String jsonValue = new ObjectMapper().writeValueAsString(obj);
            rabbitTemplate.convertAndSend(
                    RabbitConfig.EXCHANGE_NAME,
                    "relief.data",
                    jsonValue,
                    message -> { // MessagePostProcessor (because we have PQ)
                        message.getMessageProperties().setPriority(priority);
                        return message;
                    });
            log.info("Added DATA to PQ: {}", jsonValue);
        } catch (
                JsonProcessingException e) {
            log.error("ERROR parsing Relief data: {}", obj);
        }

    }

    private int getPriority(Criticality pq) {
        return switch (pq) {
            case VERY_HIGH -> 3;
            case HIGH -> 2;
            default -> 1;
        };
    }

//    PLAIN PRODUCER
//    public void sendMessage(String message) {
//        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, "relief", message);
//    }
}
