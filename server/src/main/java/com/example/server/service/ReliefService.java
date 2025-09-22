package com.example.server.service;

import java.util.List;
import java.util.Optional;

import com.example.server.model.DTOs.ReliefDTO;
import com.example.server.model.ReliefReq;
import com.example.server.model.ReliefReqStatus;
import com.example.server.model.enums.Criticality;
import com.example.server.model.enums.Keys;
import com.example.server.model.enums.ReliefStatus;
import com.example.server.repository.ReliefRequestRepository;
import com.example.server.repository.ReliefStatusRepository;
import com.example.server.service.rabbit.Producer;
import com.example.server.service.redis.RedisCacheService;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ReliefService {

    @Autowired
    private ReliefRequestRepository reliefRepository;

    @Autowired
    private ReliefStatusRepository statusRepository;

    @Autowired
    private RedisCacheService cache;

    @Autowired
    private Producer producer;

    @Autowired
    private AIService aiService;

    public void createRelief(ReliefDTO dto) {
        ReliefReq relief = new ReliefReq(
                dto.getName(),
                dto.getPoc(),
                dto.getLongitude(),
                dto.getLatitude(),
                dto.getDescription()
        );

        String criticality = aiService.getCriticality(relief.getDescription());
        Criticality crt = Criticality.valueOf(criticality);
        relief.setCriticality(crt);

        log.info("[RELIEF_S] AI analysis: {}, Status: {}", criticality, crt);

        ReliefReq savedReq = reliefRepository.save(relief);
        cache.setCache(Keys.key(Keys.RELIEF, savedReq.getUid()), savedReq, 240);

        ReliefReqStatus reliefStatus = new ReliefReqStatus(savedReq.getUid(), ReliefStatus.CREATED);
        ReliefReqStatus savedStatus = statusRepository.save(reliefStatus);
        cache.setCache(Keys.key(Keys.STATUS, savedStatus.getReliefId()), savedStatus, 240);

        String key = Keys.key(Keys.RELIEF, dto.getPoc());
        List<ReliefReq> item = cache.getCache(key, new TypeReference<List<ReliefReq>>() {});
        if (item == null) {
            log.info("[RELIEF_S] No List<ReliefReq> yet for: {}", key);
        } else {
            item.add(savedReq);
            cache.setCache(key, item, 60);
        }

        producer.sendMessage(savedReq, crt);
    }

    public List<ReliefReq> getAllRequests(String contact) {
        String key = Keys.key(Keys.RELIEF, contact);
        List<ReliefReq> item = cache.getCache(key, new TypeReference<List<ReliefReq>>() {});
        if (item != null) {
            log.info("[CACHE] getUserByUsername from cache: {}", item);
            return item;
        }
        List<ReliefReq> reliefs = reliefRepository.findByPoc(contact);

        if (reliefs == null || reliefs.isEmpty()) {
            return null;
        }
        cache.setCache(key, reliefs, 60);
        return reliefs;
    }

    // ReliefReqStatus
    public ReliefReqStatus getRequestDetail(Long reliefId) {
        return statusRepository.findById(reliefId).orElse(null);
    }

    // update ReliefReqStatus
    public ReliefReqStatus updateReliefStatus(ReliefReqStatus req) {
        return statusRepository.save(req);
    }

    public ReliefReq getReliefRequest(String reliefId) {
        String key = Keys.key(Keys.RELIEF, reliefId);
        ReliefReq item = cache.getCache(key, ReliefReq.class);
        if (item != null) {
            log.info("[CACHE] getUserByUsername from cache: {}", item);
            return item;
        }
        long rId = Long.parseLong(reliefId);
        Optional<ReliefReq> relief = reliefRepository.findById(rId);

        if (relief.isPresent()) {
            cache.setCache(key, relief.get(), 60);
            return relief.get();
        }
        return null;
    }
}
