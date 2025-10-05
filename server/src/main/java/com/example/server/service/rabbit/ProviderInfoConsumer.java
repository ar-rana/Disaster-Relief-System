package com.example.server.service.rabbit;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.server.config.RabbitConfig;
import com.example.server.model.DTOs.ProviderInfo;
import com.example.server.model.ReliefReq;
import com.example.server.model.ReliefReqStatus;
import com.example.server.model.User;
import com.example.server.service.ProviderService;
import com.example.server.service.ReliefService;
import com.example.server.service.UserService;
import com.example.server.service.redis.RedisListService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.graphhopper.GHRequest;
import com.graphhopper.GHResponse;
import com.graphhopper.GraphHopper;
import com.graphhopper.config.CHProfile;
import com.graphhopper.config.Profile;
import com.graphhopper.json.Statement;
import com.graphhopper.util.CustomModel;
import com.graphhopper.util.Parameters;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProviderInfoConsumer {

    @Autowired
    private RedisListService lists;

    @Autowired
    private ReliefService reliefService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProviderService providerService;

    GraphHopper graphHopper;

    public ProviderInfoConsumer() {
        graphHopper = new GraphHopper()
                .setOSMFile("src/main/resources/static/eastern-zone-251003.osm.pbf")
                .setGraphHopperLocation("target/heading-graph-cache")
                .setEncodedValuesString("car_access, road_access, car_average_speed")
                .setProfiles(new Profile("car")
                        .setCustomModel(new CustomModel()
                                .addToSpeed(Statement.If("true", Statement.Op.LIMIT, "car_average_speed"))
                                .addToPriority(Statement.If("!car_access", Statement.Op.MULTIPLY, "0")))
                );
        graphHopper.getCHPreparationHandler().setCHProfiles(new CHProfile("car"));
        graphHopper.importOrLoad();
    }

    @RabbitListener(queues = RabbitConfig.ASSIGNEE_EXCHANGE)
    public void listen(String msg) {
        log.info("[R_MQ-ALGO_CONSUMER] Received DATA from PQ: {}", msg);
        ObjectMapper o = new ObjectMapper();
        String key = null;
        try {
            key = o.readValue(msg, String.class);
        } catch (
                Exception ex) {
            log.error("[R_MQ-ALGO_CONSUMER] Failed to PARSE Relief OBJECT: {}", ex.getMessage());
            return;
        }

        // FETCH list FROM REDIS
        // APPLY HEURISTIC ALGORITHM HERE
        log.info("[R_MQ-ALGO_CONSUMER] PROCESSING REDIS list: {}", key);

        // key looks like -> assignees/{reqId}
        ReliefReq aid = reliefService.getReliefRequest(key.substring(10));
        List<ProviderInfo> providers = lists.getList(key, ProviderInfo.class);

        ProviderInfo closest = null;
        double shortest = Double.POSITIVE_INFINITY;
        boolean existing = false;
        while (!existing) {
            for (ProviderInfo info : providers) {
                // using Dijkstra's algorithm from both sides (bidirectional)
                GHRequest routes = new GHRequest(
                        aid.getLatitude(), aid.getLongitude(),
                        info.getLatitude(), info.getLongitude()
                ).setProfile("car").setAlgorithm(Parameters.Algorithms.DIJKSTRA_BI);
                GHResponse rsp = graphHopper.route(routes);

                double small = rsp.getBest().getDistance();
                log.info("[R_MQ-ALGO_CONSUMER] Path distance compute: {}", small);
                if (small < shortest) {
                    shortest = small;
                    closest = info;
                }
            }
            // check if new Providers have responded to ws broadcast
            Set<ProviderInfo> moreProviders = newProviders(providers, key);

            existing = moreProviders.isEmpty();
            providers = moreProviders.stream().toList();
        }

        if (closest == null) {
            User provider = shortestPathFallback(aid);
            providerService.assignRelief(aid, provider.getUsername());
        } else {
            User provider = userService.getUserByUsername(closest.getUser());
            providerService.assignRelief(aid, provider.getUsername());
        }
    }

    public Set<ProviderInfo> newProviders(List<ProviderInfo> providers, String key) {
        List<ProviderInfo> temp = lists.getList(key, ProviderInfo.class);
        Set<ProviderInfo> set = new HashSet<>(temp);

        for (ProviderInfo info : providers) {
            set.remove(info);
        }
        return set;
    }

    public User shortestPathFallback(ReliefReq aid) {
        ReliefReqStatus status = reliefService.getRequestDetail(aid.getUid());
        return userService.getRandomProviderFromHq(status.getHqId());
    }
}
