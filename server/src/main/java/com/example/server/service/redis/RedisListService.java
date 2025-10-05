package com.example.server.service.redis;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.example.server.model.DTOs.Coords;
import com.example.server.service.rabbit.Producer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RedisListService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private Producer producer;

    public <T> void insertInList(String key, Object obj, Class<T> entityClass) {
        boolean exits = redisTemplate.hasKey(key);
        try {
            String jsonValue = new ObjectMapper().writeValueAsString(obj);
            redisTemplate.opsForList().rightPush(key, jsonValue);
            redisTemplate.expire(key, Duration.ofMinutes(10));
        } catch (JsonProcessingException e) {
            log.error("[REDIS_List] Could not parse Object for key: {}", key);
        }
        if (!exits) {
            producer.sendProviderInfoKey(key);
            log.info("[REDIS_List] Sent ProviderInfo: {} to redis list(key): {}", obj, key);
        }
    }

    public <T> List<T> getList(String key, Class<T> entityClass) {
        List<String> jsonList = redisTemplate.opsForList().range(key, 0, -1);
        if (jsonList == null) return null;
        ObjectMapper mapper = new ObjectMapper();

        List<T> lt = new ArrayList<>();
        for (String json: jsonList) {
            try {
                T item = mapper.readValue(json, entityClass);
                lt.add(item);
            } catch (JsonProcessingException ex) {
                log.error("[REDIS_List] Could not deserialize Object for key: {}", key);
            }
        }

        log.info("[REDIS_List] Fetched providerInfo from Redis with key: {}, list: {}",key, lt);
        return lt;
    }

    public <T> void insertBlockedCords(Coords obj) {
        String key = "BLOCK_COORDINATES";
        try {
            String jsonValue = new ObjectMapper().writeValueAsString(obj);
            redisTemplate.opsForList().rightPush(key, jsonValue);
        } catch (JsonProcessingException e) {
            log.error("[REDIS_List] Could not parse Object for key: {}", key);
        }
    }

    public Set<Coords> getBlockedCoords() {
        String key = "BLOCK_COORDINATES";
        List<String> jsonList = redisTemplate.opsForList().range(key, 0, -1);
        if (jsonList == null) return null;
        ObjectMapper mapper = new ObjectMapper();

        Set<Coords> set = new HashSet<>();
        for (String json: jsonList) {
            try {
                Coords item = mapper.readValue(json, Coords.class);
                set.add(item);
            } catch (JsonProcessingException ex) {
                log.error("[REDIS_List] Could not deserialize Object for key: {}", key);
            }
        }

        log.info("[REDIS_List] Fetched BlockedCords from Redis list: {}", set);
        return set.isEmpty() ? null: set;
    }
}
