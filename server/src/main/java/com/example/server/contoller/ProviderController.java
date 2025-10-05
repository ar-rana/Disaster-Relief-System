package com.example.server.contoller;

import java.util.ArrayList;
import java.util.FormatFlagsConversionMismatchException;
import java.util.List;
import java.util.Map;

import com.example.server.model.DTOs.Coords;
import com.example.server.model.DTOs.ReliefStatusDTO;
import com.example.server.model.ReliefReq;
import com.example.server.service.ProviderService;
import com.example.server.service.redis.RedisListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("provider")
public class ProviderController {

    @Autowired
    private ProviderService providerService;

    @Autowired
    private RedisListService lists;

    @PostMapping("/resolveRelief")
    public ResponseEntity<String> resolveRelief(@RequestBody ReliefStatusDTO request) {
        if (request.getReliefId() == null || request.getImages() == null || request.getDesc() == null ||
                request.getImages().isEmpty() || request.getDesc().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("All fields are required to resolveRelief");
        }
        providerService.resolveRelief(request);

        return ResponseEntity.ok("Relief resolved successfully");
    }

    @PostMapping("/rejectRelief")
    public ResponseEntity<String> rejectRelief(@RequestBody ReliefStatusDTO request) {
        if (request.getReliefId() == null || request.getDesc() == null ||
                request.getDesc().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("reliefId and desc are required for rejectRelief");
        }
        providerService.rejectRelief(request);

        return ResponseEntity.ok("Relief rejected successfully");
    }

    @GetMapping("/get/assignments/{username}")
    public ResponseEntity<?> getAssignedReliefs(@PathVariable String username) {
        if (username == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("reliefId and desc are required for rejectRelief");
        }
        List<ReliefReq> res = providerService.getAssignedReliefs(username);

        return ResponseEntity.ok(res);
    }

    //
    @PostMapping("/report/blockedpath")
    public ResponseEntity<?> setPathAsBlocked(@RequestBody Coords item) {
        if (item.getLongitude() == 0.0 || item.getLatitude() == 0.0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("BlockedCords cannot be 0");
        }

        lists.insertBlockedCords(item);
        return ResponseEntity.ok("You coords have been reported");
    }

    //
    @PostMapping("/route")
    public ResponseEntity<List<Coords>> getSafestRoute(@PathVariable Map<String, String> item) {
        try {
            double longitude = Double.parseDouble(item.get("longitude"));
            double latitude = Double.parseDouble(item.get("latitude"));
            String reqId = item.get("reqId");
            if (reqId == null || reqId.isEmpty()) {
                throw new NullPointerException("No reqId found");
            }

            List<Coords> res = providerService.getSafestRoute(reqId, new Coords(latitude, longitude));
            return ResponseEntity.ok(res);
        } catch (NumberFormatException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ArrayList<>());
        } catch (NullPointerException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ArrayList<>());
        }
    }
}
