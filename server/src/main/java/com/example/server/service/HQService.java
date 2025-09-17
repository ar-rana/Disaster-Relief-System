package com.example.server.service;

import java.util.Optional;

import com.example.server.model.HeadQuarters;
import com.example.server.model.enums.Keys;
import com.example.server.repository.HeadQuarterRepository;
import com.example.server.service.redis.RedisCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HQService {

    @Autowired
    private HeadQuarterRepository hqRepository;

    @Autowired
    private RedisCacheService cache;

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
//        String user = SecurityContextHolder.getContext().getAuthentication().getName();
//        User controler = userService.getUserByUsername(user);
//        hqId = controler.getHeadQuarters().getHqId();
        HeadQuarters hq = getHeadquartersById(hqId);
        if (hq == null) {
            return "HeadQuarters do not exits!";
        }
        hq.setResourceUnits(hq.getResourceUnits() + additionalResources);
        hqRepository.save(hq);

        return "new resources allocated successfully!!";
    }

    public String createHeadquarters(double longitude, double latitude, String address, int resource) {
        HeadQuarters hq = new HeadQuarters(address, longitude, latitude, resource);
        hqRepository.save(hq);
        return "NEW HQ added Successfully";
    }
}
