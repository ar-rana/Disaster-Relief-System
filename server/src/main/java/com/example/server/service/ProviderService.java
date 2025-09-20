package com.example.server.service;

import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.server.model.DTOs.ReliefStatusDTO;
import com.example.server.model.ReliefReq;
import com.example.server.model.ReliefReqStatus;
import com.example.server.model.enums.Keys;
import com.example.server.model.enums.ReliefStatus;
import com.example.server.service.redis.RedisCacheService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.FieldValue;
import com.google.cloud.firestore.Firestore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class ProviderService {

    @Autowired
    private Firestore firestore;

    @Autowired
    private RedisCacheService cache;

    @Autowired
    private ReliefService reliefService;

    private static final String SEARCH_DB = "assigned_reliefs";

    public void resolveRelief(ReliefStatusDTO request) {
        ReliefReqStatus status = new ReliefReqStatus(request.getReliefId(), ReliefStatus.COMPLETED);
        status.setDescription(request.getDesc());
        List<byte[]> lt = new ArrayList<>();
        for (MultipartFile file: request.getImages()) {
            try {
                lt.add(file.getBytes());
            } catch (IOException e) {
                log.error("[RELIEF-STATUS] problem converting multipart to bytes");
            }
        }
        status.setImages(lt);

        reliefService.updateReliefStatus(status);
    }

    public void rejectRelief(ReliefStatusDTO request) {
        ReliefReqStatus status = new ReliefReqStatus(request.getReliefId(), ReliefStatus.SUSPENDED);
        status.setDescription(request.getDesc());

        reliefService.updateReliefStatus(status);
    }

    public void assignRelief(ReliefReq request, String username) {
        var docRef = firestore.collection(SEARCH_DB).document(username);

        try {
            docRef.update("requests", FieldValue.arrayUnion(request)).get(); // update doc if exists
        } catch (Exception e) {
            // create if not exist
            Map<String, List<ReliefReq>> map = new HashMap<>();
            map.put("productIds", List.of(request));
            docRef.set(map);
        }
        log.info("[FIRESTORE] Request : {} assigned to: {}", request, username);
        String key = Keys.key(Keys.RELIEF, "list/" + username);
        List<ReliefReq> item = cache.getCache(key, new TypeReference<List<ReliefReq>>() {});
        if (item != null) {
            item.add(request);
            cache.setCache(key, item, 240);
        } else {
            cache.setCache(key, List.of(request), 240);
        }

        ReliefReqStatus status = reliefService.getRequestDetail(request.getUid());
        status.setStatus(ReliefStatus.IN_PROGRESS);
        status.setDescription("your relief has been assigned, help is on its way!!");
        reliefService.updateReliefStatus(status);

        // send to user through WEBSOCKETS
    }

    public void removeRelief(ReliefReq request, String username) {
        var docRef = firestore.collection(SEARCH_DB).document(username);

        try {
            // remove request from array
            docRef.update("requests", FieldValue.arrayRemove(request)).get();
            log.info("[FIRESTORE] Removed request : {} for user: {}", request, username);
            // update cache
            cache.deleteCache(Keys.key(Keys.RELIEF, "list/" + username));
        } catch (Exception e) {
            log.error("[FIRESTORE] Failed to remove request: {} for user: {}, err: {}", request, username, e.getMessage());
        }
    }

    public List<ReliefReq> getAssignedReliefs(String username) {
        String key = Keys.key(Keys.RELIEF, "list/" + username);
        List<ReliefReq> item = cache.getCache(key, new TypeReference<List<ReliefReq>>() {});
        if (item != null) {
            log.info("[CACHE] Firestore data fetch: {}", username);
            return item;
        }
        List<ReliefReq> reqs;
        try {
            DocumentSnapshot snapshot = firestore.collection(SEARCH_DB).document(username).get().get();
            if (snapshot.exists()) {
                reqs = (List<ReliefReq>) snapshot.get("requests");
                cache.setCache(key, reqs, 240);
                return reqs;
            }
        } catch (InterruptedException e) {
            log.error("[FIRESTORE] query interrupted: {}", e.getMessage());
        } catch (Exception ex) {
            log.error("[FIRESTORE] Failed to fetch: {}", ex.getMessage());
        }
        return null;
    }
}
