package com.example.server.service;

import com.example.server.model.HeadQuarters;
import com.example.server.model.User;
import com.example.server.model.enums.Keys;
import com.example.server.model.enums.UserType;
import com.example.server.repository.UserRepository;
import com.example.server.service.redis.RedisCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public String createUser(String name, String username, String password) {
        User existingUser = userService.getUserByUsername(username);
        if (existingUser != null) {
            return "User Already Exists!!";
        }
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        User controller = userService.getUserByUsername(user);
        if (controller.getRole() != UserType.ADMIN) {
            return "unauthorized user";
        }

        User admin = new User(username, name, username, password);
        admin.setRole(UserType.ADMIN);
        int hqId = controller.getHeadQuarters().getHqId();

        admin.setHeadQuarters(hqService.getHeadquartersById(hqId));
        admin.setPassword((encoder.encode(admin.getPassword())));
        User savedUser = repository.save(admin);

        cache.setCache(Keys.key(Keys.USER, savedUser.getUsername()), savedUser, 120);

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
        cache.setCache(Keys.key(Keys.USER, newProvider.getUsername()), newProvider, 120);

        return "Provider Updated Successfully, New HQ: " + newProvider.getHeadQuarters();
    }

    public String changePassword(String password) {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        User admin = userService.getUserByUsername(user);

        admin.setPassword((encoder.encode(admin.getPassword())));

        return "password changed successfully";
    }

    public User superUser(String username, String password, String name, Integer hqId) {
        User admin = new User(username, name, username, password);
        admin.setRole(UserType.ADMIN);

        HeadQuarters hq = hqService.getHeadquartersById(hqId);

        admin.setHeadQuarters(hq);
        admin.setPassword((encoder.encode(admin.getPassword())));

        return repository.save(admin);
    }


}
