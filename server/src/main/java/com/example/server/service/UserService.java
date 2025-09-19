package com.example.server.service;

import java.util.Optional;

import com.example.server.model.User;
import com.example.server.model.enums.Keys;
import com.example.server.repository.UserRepository;
import com.example.server.service.redis.RedisCacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private RedisCacheService cache;

    public String createUser(String name, String username, String password) {
        User existingUser = getUserByUsername(username);
        if (existingUser != null) {
            return "User Already Exists!!";
        }
        //        String user = SecurityContextHolder.getContext().getAuthentication().getName();
//        User controler = userService.getUserByUsername(user);
        User provider = new User(username, name, username, password); // username is also the number
        //        int hqId = controler.getHeadQuarters().getHqId();
//        admin.setHeadQuarters(getHeadquartersById(hqId));
        User savedUser = repository.save(provider);
        cache.setCache(Keys.key(Keys.PROVIDER, savedUser.getUsername()), savedUser, 120);

        return "User registered Successfully";
    }

    public String login(String username, String password) {
        User existingUser = getUserByUsername(username);
        if (existingUser == null) {
            return "User Does Not Exist!!";
        }
        return "";
    }

    public User getUserByUsername(String username) {
        String key = Keys.key(Keys.PROVIDER, username);
        User item = cache.getCache(key, User.class);
        if (item != null) {
            log.info("[CACHE] getUserByUsername from cache: {}", item);
            return item;
        }
        Optional<User> user = repository.findById(username);
        if (user.isPresent()) {
            cache.setCache(key, user.get(), 120);
            return user.get();
        }
        return null;
    }
}
