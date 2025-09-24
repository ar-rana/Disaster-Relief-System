package com.example.server.contoller;

import java.util.List;
import java.util.Map;

import com.example.server.model.DTOs.ReliefDTO;
import com.example.server.model.ReliefReq;
import com.example.server.model.ReliefReqStatus;
import com.example.server.model.enums.Criticality;
import com.example.server.service.CommunicationService;
import com.example.server.service.ReliefService;
import com.example.server.service.rabbit.Producer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @Autowired
    private CommunicationService comms;

    private final SimpMessagingTemplate messagingTemplate;
    public ReliefController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody ReliefDTO item) {
        log.info("[RELIEF] Relief request received: {}", item.toString());
        if (item.getDescription() == null || item.getLongitude() == 0 || item.getLatitude() == 0 ||
            item.getPoc() == null || item.getDescription().isEmpty() || item.getPoc().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("All fields are required");
        }
        ReliefReq res = reliefService.createRelief(item);

        messagingTemplate.convertAndSend("/navigation", res);
        return ResponseEntity.ok("we have received you request we will soon be sending help");
    }

    @GetMapping("/getstatus/all/{contact}")
    public ResponseEntity<List<ReliefReq>> getAllRequests(@PathVariable String contact) {
        log.info("[RELIEF] Getting all requests for: {}", contact);

        List<ReliefReq> res = reliefService.getAllRequests(contact);
        if (res == null || res.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(res);
        }
        return ResponseEntity.ok(res);
    }

    @GetMapping("/getrelief/{reliefId}")
    public ResponseEntity<ReliefReq> getReliefReq(@PathVariable String reliefId) {
        log.info("[RELIEF] Getting requests for: {}", reliefId);

        ReliefReq res = reliefService.getReliefRequest(reliefId);
        if (res == null) {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
        return ResponseEntity.ok(res);
    }

    @GetMapping("/getstatus/{reliefId}")
    public ResponseEntity<?> getRequestDetail(@PathVariable String reliefId) {
        log.info("[RELIEF] Relief Details: {}", reliefId);
        long reliefUid;
        try {
            reliefUid = Long.parseLong(reliefId);
        } catch (NumberFormatException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("invalid reliefId");
        }
        ReliefReqStatus req = reliefService.getRequestDetail(reliefUid);
        if (req == null) {
            log.error("Relief does not exist: {}", reliefUid);
            return ResponseEntity.status(HttpStatus.OK).body("invalid reliefId");
        }
        return ResponseEntity.ok(req);
    }
}
