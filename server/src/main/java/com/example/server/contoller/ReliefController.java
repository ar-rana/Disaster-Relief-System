package com.example.server.contoller;

import java.util.Map;

import com.example.server.model.ReliefReq;
import com.example.server.model.enums.Criticality;
import com.example.server.service.rabbit.Producer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private Producer publish;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody ReliefReq item) {
        log.info("Relief request received: {}", item.toString());
//        item.setCriticality(Criticality.BASIC);
//        publish.sendMessage(item, Criticality.BASIC);
//        publish.sendMessage(item, Criticality.BASIC);
//        publish.sendMessage(item, Criticality.BASIC);
//        item.setCriticality(Criticality.VERY_HIGH);
//        publish.sendMessage(item, Criticality.VERY_HIGH);
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
