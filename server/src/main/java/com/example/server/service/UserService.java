package com.example.server.service;

import java.util.Optional;

import com.example.server.model.User;
import com.example.server.model.enums.Keys;
import com.example.server.model.enums.UserType;
import com.example.server.repository.UserRepository;
import com.example.server.service.redis.RedisCacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private RedisCacheService cache;

//    @Autowired
//    private HQService hqService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public String createUser(String name, String username, String password) {
        User existingUser = getUserByUsername(username);
        if (existingUser != null) {
            return "User Already Exists!!";
        }
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        User controller = getUserByUsername(user);
        if (controller.getRole() != UserType.ADMIN) {
            return "unauthorized user";
        }

        User provider = new User(username, name, username, password); // username is also the number
//        int hqId = controller.getHeadQuarters().getHqId();
        log.info("[USER_S] Setting provider HQ: {}", controller.getHeadQuarters());
        provider.setHeadQuarters(controller.getHeadQuarters());

        provider.setPassword((encoder.encode(provider.getPassword())));
        User savedUser = repository.save(provider);

        cache.setCache(Keys.key(Keys.USER, savedUser.getUsername()), savedUser, 120);

        return "User registered Successfully: " + savedUser.getUsername();
    }

    public String login(String username, String password) {
        User user = getUserByUsername(username);
        if (user == null) return null;
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), password));

        if(authentication.isAuthenticated())
            return jwtService.generateToken(user.getUsername(), user.getRole());
        else
            return null;
    }

    public User getUserByUsername(String username) {
        String key = Keys.key(Keys.USER, username);
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
