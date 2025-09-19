package com.example.server.service;

import java.util.List;

import com.example.server.model.DTOs.ReliefDTO;
import com.example.server.model.ReliefReq;
import com.example.server.model.ReliefReqStatus;
import com.example.server.model.enums.Keys;
import com.example.server.model.enums.ReliefStatus;
import com.example.server.repository.ReliefRequestRepository;
import com.example.server.repository.ReliefStatusRepository;
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

    public void createRelief(ReliefDTO dto) {
        ReliefReq relief = new ReliefReq(
                dto.getName(),
                dto.getPoc(),
                dto.getLongitude(),
                dto.getLatitude(),
                dto.getDescription()
        );

        ReliefReq savedReq = reliefRepository.save(relief);
        cache.setCache(Keys.key(Keys.RELIEF, savedReq.getUid()), savedReq, 240);

        ReliefReqStatus reliefStatus = new ReliefReqStatus(savedReq.getUid(), ReliefStatus.CREATED);
        ReliefReqStatus savedStatus = statusRepository.save(reliefStatus);
        cache.setCache(Keys.key(Keys.STATUS, savedStatus.getReliefId()), savedStatus, 240);

        String key = Keys.key(Keys.PROVIDER, dto.getPoc());
        List<ReliefReq> item = cache.getCache(key, new TypeReference<List<ReliefReq>>() {});
        if (item == null) {
            log.info("No List<ReliefReq> yet for: {}", key);
        } else {
            item.add(savedReq);
            cache.setCache(key, item, 60);
        }
    }

    public List<ReliefReq> getAllRequests(String contact) {
        String key = Keys.key(Keys.PROVIDER, contact);
        List<ReliefReq> item = cache.getCache(key, new TypeReference<List<ReliefReq>>() {});
        if (item != null) {
            log.info("getUserByUsername from cache: {}", item);
            return item;
        }
        List<ReliefReq> reliefs = reliefRepository.findByPoc(contact);

        if (reliefs == null || reliefs.isEmpty()) {
            return null;
        }
        cache.setCache(Keys.key(Keys.RELIEF, contact), reliefs, 60);
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
}
