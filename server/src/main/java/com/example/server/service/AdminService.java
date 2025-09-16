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
    private HQService hqService;

    public String createUser(String name, String username, String password) {
        User existingUser = userService.getUserByUsername(username);
        if (existingUser != null) {
            return "User Already Exists!!";
        }
//        String user = SecurityContextHolder.getContext().getAuthentication().getName();
//        User controler = userService.getUserByUsername(user);
        User admin = new User(username, name, username, password);
        admin.setRole(UserType.ADMIN);
//        int hqId = controler.getHeadQuarters().getHqId();
//        admin.setHeadQuarters(getHeadquartersById(hqId));
        User savedUser = repository.save(admin);
        cache.setCache(Keys.key(Keys.PROVIDER, savedUser.getUsername()), savedUser, 120);

        return "Admin registered Successfully!!";
    }

    public String transferProvider(String contact, int hqId) {
        User provider = userService.getUserByUsername(contact);
        HeadQuarters hq = hqService.getHeadquartersById(hqId);
        if (hq == null) {
            return "HeadQuarters do not exits!!";
        }
        if (provider == null) {
            return "Enter correct identification for provider!!";
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


}
