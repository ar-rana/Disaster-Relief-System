package com.example.server.service.redis;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RedisCacheService {

    @Autowired
    private RedisTemplate redisTemplate;
    
    public <T> T getCache(String key, Class<T> entityClass) {
        Object o = redisTemplate.opsForValue().get(key);
        if (o == null) {
            log.info("[CACHE_S] Object Null in Cache with key: {}", key);
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        log.info("[CACHE_S] Object as String from Cache for: {}", entityClass);
        try {
            return mapper.readValue((String) o, entityClass);
        } catch (Exception ex) {
            log.error("[CACHE_S] Failed to PARSE OBJECT: {}", ex.getMessage());
        }
        return null;
    }
    public <T> T getCache(String key, TypeReference<T> typeReference) {
        Object o = redisTemplate.opsForValue().get(key);
        if (o == null) {
            log.info("[CACHE_S] Object Null in Cache with key: {}", key);
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        log.info("[CACHE_S] Object as String from Cache for: {}", typeReference);
        try {
            return mapper.readValue((String) o, typeReference);
        } catch (JsonProcessingException ex) {
            log.error("[CACHE_S] Failed to PARSE OBJECT: {}", ex.getMessage());
        }
        return null;
    }

    public void setCache(String key, Object o, Integer ttl) {
        try {
            String jsonValue = new ObjectMapper().writeValueAsString(o);
            redisTemplate.opsForValue().set(key, jsonValue, ttl, TimeUnit.MINUTES);
        } catch (Exception ex) {
            log.info("[CACHE_S] Failed to SAVE in cache: {}", ex.getMessage());
        }
        log.info("[CACHE_S] ADDED: {}", key);
    }

    public void deleteCache(String key) {
        try {
            redisTemplate.delete(key);
        } catch (Exception ex) {
            log.error("Failed to DELETE cache: " + ex.getMessage());
        }
        log.warn("[CACHE_S] DELETED: {}", key);
    }
}
