package com.example.server.service.rabbit;

import java.util.List;
import java.util.Map;

import com.example.server.config.RabbitConfig;
import com.example.server.model.HeadQuarters;
import com.example.server.model.ReliefReq;
import com.example.server.model.ReliefReqStatus;
import com.example.server.model.User;
import com.example.server.model.enums.Keys;
import com.example.server.model.enums.ReliefStatus;
import com.example.server.service.HQService;
import com.example.server.service.ProviderService;
import com.example.server.service.ReliefService;
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

    @Autowired
    private HQService hqService;

    @Autowired
    private ReliefService reliefService;

    private final SimpMessagingTemplate messagingTemplate;
    public Consumer(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @RabbitListener(queues = RabbitConfig.QUEUE_NAME)
    public void listen(String msg) {
        log.info("[R_MQ-RELIEF_CONSUMER] Received DATA from PQ: {}", msg);
        ObjectMapper o = new ObjectMapper();
        ReliefReq rq = null;
        try {
            rq = o.readValue((String) msg, ReliefReq.class);
        } catch (Exception ex) {
            log.error("[R_MQ-RELIEF_CONSUMER] Failed to PARSE Relief OBJECT: {}", ex.getMessage());
            return;
        }

        // SEND ws BROADCAST for RELIEF-REQ
        log.info("[R_MQ-RELIEF_CONSUMER] PROCESSING: {} sending websocket broadcast", rq);
        List<HeadQuarters> hqs = hqService.getAllHQ();

        HeadQuarters closest = null;
        double smallestDist = Double.POSITIVE_INFINITY;
        for (HeadQuarters hq: hqs) {
            double small = getManhattanDistance(rq, hq);
            if (small < smallestDist) {
                smallestDist = small;
                closest = hq;
            }
        }

        log.info("[R_MQ-RELIEF_CONSUMER] Closest HQ: {}", closest.getHqId());
        ReliefReqStatus status = new ReliefReqStatus(rq.getUid(), ReliefStatus.CREATED);
        status.setDescription("We have received you request, a provider for you request will be assigned soon");
        status.setHqId(closest.getHqId());
        reliefService.updateReliefStatus(status);

        messagingTemplate.convertAndSend("/navigation", Map.of("shareCredentials", true, "reqId", rq.getUid(), "hq", closest.getHqId()));
    }

    public static double getManhattanDistance(ReliefReq rq, HeadQuarters hq) {
        double x = Math.abs(rq.getLatitude() - hq.getLatitude());
        double y = Math.abs(rq.getLongitude() - hq.getLongitude());
        return x + y;
    }

    public static double getEuclideanDistance(ReliefReq rq, HeadQuarters hq) {
        double x = Math.pow(rq.getLatitude() - hq.getLatitude(), 2);
        double y = Math.pow(rq.getLongitude() - hq.getLongitude(), 2);
        return Math.pow(x + y, 0.5);
    }
}
