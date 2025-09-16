package com.example.server.service;

import java.util.Optional;

import com.example.server.model.HeadQuarters;
import com.example.server.model.User;
import com.example.server.model.enums.Keys;
import com.example.server.model.enums.UserType;
import com.example.server.repository.HeadQuarterRepository;
import com.example.server.repository.UserRepository;
import com.example.server.service.redis.RedisCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository repository;

    @Autowired
    private RedisCacheService cache;

    @Autowired
    private HeadQuarterRepository hqRepository;

    public String createUser(String name, String username, String password) {
        User existingUser = userService.getUserByUsername(username);
        if (existingUser != null) {
            return "User Already Exists!!";
        }
        User admin = new User(username, name, username, password);
        admin.setRole(UserType.ADMIN);
        User savedUser = repository.save(admin);
        cache.setCache(Keys.key(Keys.PROVIDER, savedUser.getUsername()), savedUser, 120);

        return "Admin registered Successfully!!";
    }

    public String transferProvider(String contact, int hqId) {
        User provider = userService.getUserByUsername(contact);
        HeadQuarters hq = getHeadquartersById(hqId);
        if (hq == null) {
            return "HeadQuarters do not exits!!";
        }
        provider.setHeadQuarters(hq);
        User newProvider = repository.save(provider);
        cache.setCache(Keys.key(Keys.PROVIDER, newProvider.getUsername()), newProvider, 120);

        return "Provider Updated Successfully";
    }

    public String changePassword(String username, String password) {
        User admin = userService.getUserByUsername(username);
        // check if username matches Context.auth
        return "";
    }

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

    public String updateHQResources(int additionalResources, int hqId) {
        HeadQuarters hq = getHeadquartersById(hqId);
        if (hq == null) {
            return "HeadQuarters do not exits!";
        }
        hq.setResourceUnits(hq.getResourceUnits() + additionalResources);
        hqRepository.save(hq);

        return "new resources allocated successfully!!";
    }
}
