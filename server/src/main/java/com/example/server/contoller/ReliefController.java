package com.example.server.contoller;

import java.util.List;
import java.util.Map;

import com.example.server.model.DTOs.ReliefDTO;
import com.example.server.model.ReliefReq;
import com.example.server.model.ReliefReqStatus;
import com.example.server.model.enums.Criticality;
import com.example.server.service.ReliefService;
import com.example.server.service.rabbit.Producer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("request")
@Slf4j
public class ReliefController {

    @Autowired
    private ReliefService reliefService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody ReliefDTO item) {
        log.info("Relief request received: {}", item.toString());
        reliefService.createRelief(item);
        return ResponseEntity.ok("we have received you request we will soon be sending help");
    }

    @GetMapping("/getstatus/all/{contact}")
    public ResponseEntity<List<ReliefReq>> getAllRequests(@RequestParam String contact) {
        log.info("Getting all requests for: {}", contact);

        List<ReliefReq> res = reliefService.getAllRequests(contact);
        if (res == null || res.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(res);
        }
        return ResponseEntity.ok(res);
    }

    @GetMapping("/getstatus/{reliefId}")
    public ResponseEntity<?> getRequestDetail(@RequestBody String reliefId) {
        log.info("Request access to Relief: {}", reliefId);
        long reliefUid;
        try {
            reliefUid = Long.parseLong(reliefId);
        } catch (NumberFormatException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("invalid reliefId");
        }
        ReliefReqStatus req = reliefService.getRequestDetail(reliefUid);
        if (req == null) {
            log.error("Relief does not exist: {}", reliefUid);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("invalid reliefId");
        }
        return ResponseEntity.ok(req);
    }
}
