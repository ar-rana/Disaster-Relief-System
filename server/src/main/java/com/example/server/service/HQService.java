package com.example.server.service;

import java.util.List;
import java.util.Optional;

import com.example.server.model.HeadQuarters;
import com.example.server.model.User;
import com.example.server.model.enums.Keys;
import com.example.server.repository.HeadQuarterRepository;
import com.example.server.service.redis.RedisCacheService;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class HQService {

    @Autowired
    private HeadQuarterRepository hqRepository;

    @Autowired
    private RedisCacheService cache;

    @Autowired
    private UserService userService;

    public HeadQuarters getHeadquartersById(int hqId) {
        HeadQuarters item = cache.getCache(Keys.key(Keys.HQ, hqId), HeadQuarters.class);
        if (item != null) {
            return item;
        }
        Optional<HeadQuarters> hq = hqRepository.findById(hqId);
        if (hq.isPresent()) {
            cache.setCache(Keys.key(Keys.HQ, hq.get().getHqId()), hq.get(), 120);
            return hq.get();
        }
        return null;
    }

    public String updateHQResources(int additionalResources) {
        int hqId = 0;
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        User controller = userService.getUserByUsername(user);
        hqId = controller.getHeadQuarters().getHqId();

        HeadQuarters hq = getHeadquartersById(hqId);
        if (hq == null) {
            return "HeadQuarters do not exits!";
        }
        hq.setResourceUnits(hq.getResourceUnits() + additionalResources);
        HeadQuarters savedHq = hqRepository.save(hq);
        cache.setCache(Keys.key(Keys.HQ, savedHq.getHqId()), savedHq, 240);

        return "new resources allocated successfully, Total units: " + savedHq.getResourceUnits();
    }

    public String createHeadquarters(double longitude, double latitude, String address, int resource) {
        HeadQuarters hq = new HeadQuarters(address, longitude, latitude, resource);
        HeadQuarters savedHq = hqRepository.save(hq);
        cache.setCache(Keys.key(Keys.HQ, savedHq.getHqId()), savedHq, 240);

        String hqsKey = Keys.key(Keys.HQ, "all");
        List<HeadQuarters> item = cache.getCache(hqsKey, new TypeReference<List<HeadQuarters>>() {});
        if (item != null) {
            item.add(savedHq);
            cache.setCache(hqsKey, item, 300);
        }
        return "NEW HQ added Successfully, ID: " + savedHq.getHqId();
    }

    public List<HeadQuarters> getAllHQ() {
        String key = Keys.key(Keys.HQ, "all");
        List<HeadQuarters> item = cache.getCache(key, new TypeReference<List<HeadQuarters>>() {});
        if (item != null) {
            return item;
        }
        List<HeadQuarters> hqs = hqRepository.findAll();
        cache.setCache(key, hqs, 300);
        return hqs;
    }
}
