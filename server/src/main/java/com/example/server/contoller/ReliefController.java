package com.example.server.contoller;

import java.util.Map;

import com.example.server.model.ReliefReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("request")
@Slf4j
public class ReliefController {

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody ReliefReq item) {
        log.info("Relief request received: {}", item.toString());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/getstatus/all")
    public ResponseEntity<?> getAllRequests(@RequestBody String poc) {
        log.info("Request received: {}", poc);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/getstatus")
    public ResponseEntity<?> getRequestDetail(@RequestBody Map<String, String> item) {
        log.info("Request for relief enquiry: {}", item.toString());
        // item must have relief Id and phone (poc)
        return ResponseEntity.ok().build();
    }
}
