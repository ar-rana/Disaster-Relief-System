package com.example.server.service;

import java.util.Optional;

import com.example.server.model.User;
import com.example.server.model.enums.Keys;
import com.example.server.repository.UserRepository;
import com.example.server.service.redis.RedisCacheService;
import com.google.cloud.storage.Acl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private RedisCacheService cache;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String key = Keys.key(Keys.USER, username);
        User item = cache.getCache(key, User.class);
        if (item != null) {
            return new com.example.server.model.UserDetails(item);
        }
        Optional<User> user = repository.findById(username);
        if (user.isPresent()) {
            cache.setCache(key, user.get(), 240);
            return new com.example.server.model.UserDetails(user.get());
        }
        throw new UsernameNotFoundException("USER 404");
    }
}
