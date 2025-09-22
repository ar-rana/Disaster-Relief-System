package com.example.server.contoller;

import java.util.List;

import com.example.server.model.DTOs.ReliefStatusDTO;
import com.example.server.model.ReliefReq;
import com.example.server.model.ReliefReqStatus;
import com.example.server.model.enums.ReliefStatus;
import com.example.server.service.ProviderService;
import com.google.common.util.concurrent.AbstractScheduledService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("provider")
public class ProviderController {

    @Autowired
    private ProviderService providerService;

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

    // un-tested
    @GetMapping("/get/assignments/{username}")
    public ResponseEntity<?> getAssignedReliefs(@PathVariable String username) {
        if (username == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("reliefId and desc are required for rejectRelief");
        }
        List<ReliefReq> res = providerService.getAssignedReliefs(username);

        return ResponseEntity.ok(res);
    }
}
