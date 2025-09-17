package com.example.server.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.server.model.DTOs.ReliefDTO;
import com.example.server.model.ReliefReq;
import com.example.server.model.User;
import com.example.server.model.enums.Keys;
import com.example.server.repository.ReliefRequestRepository;
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

    public ReliefReq getRequestDetail(Long reliefId) {
        return reliefRepository.findById(reliefId).orElse(null);
    }
}
