package com.example.server.contoller;

import java.util.Map;

import com.example.server.service.AIService;
import com.example.server.service.AdminService;
import com.example.server.service.HQService;
import com.example.server.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin")
@Slf4j
public class AdminController {

    @Value("${app.secret.key}")
    private String secretKey;

    @Value("${app.secret.hq}")
    private String hqSecretKey;

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserService userService;

    @Autowired
    private HQService hqService;

    @PostMapping("/create/hq")
    public ResponseEntity<String> createHQ(
            @RequestBody Map<String, String> body,
            @RequestHeader(value = "x-hq-secret", required = true) String secret
    ) {
        if (!secret.equals(hqSecretKey)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED USER FOR HQ ACCESS");
        }
        String longitude = body.get("longitude");
        String latitude = body.get("latitude");
        String address = body.get("address");
        String resource = body.get("address");
        if (longitude == null || latitude == null || address == null || resource == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("enter complete data");
        }

        log.info("[ADMIN] Creating new HQ with longitude={}, latitude={}, address={}",
                longitude, latitude, address);

        String res = hqService.createHeadquarters(Double.parseDouble(longitude),
                Double.parseDouble(latitude),
                address,
                Integer.parseInt(resource)
        );

        return ResponseEntity.ok(res);
    }

    @PostMapping("/add/user")
    public ResponseEntity<String> addUser(@RequestBody Map<String, String> item) {
        log.info("Login req: {}", item.toString());
        String username = item.get("username");
        String password = item.get("password");
        String name = item.get("name");
        if (username == null || password == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("username, name, & password required");
        }
        String res = userService.createUser(name, username, password);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }

    @PostMapping("/add/admin")
    public ResponseEntity<String> addAdmin(@RequestBody Map<String, String> item) {
        String secret = item.get("AdminSecret");
        if (!secret.equals(hqSecretKey)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED USER FOR HQ ACCESS");
        }

        log.info("[ADMIN] creating admin: {}", item.toString());
        String username = item.get("username");
        String password = item.get("password");
        String name = item.get("name");
        if (username == null || password == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("username, name, & password required");
        }
        String res = adminService.createUser(name, username, password);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
    }

    @PutMapping("/update/hq/resources")
    public ResponseEntity<String> updateHqResources(@RequestBody Integer allocation) {
        log.info("[ADMIN] HQ allocation received: {}", allocation);
        String res = hqService.updateHQResources(allocation);
        return ResponseEntity.ok(res);
    }

    @PutMapping("/update/hq")
    public ResponseEntity<String> transferProvider(@RequestBody Map<String, String> item) {
        log.info("[ADMIN] Transfer request received: {}", item.toString());
        String hqId = item.get("hqId");
        String contact = item.get("contact");
        if (hqId == null || contact == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("contact and hqId required");
        }
        String res = adminService.transferProvider(contact, Integer.parseInt(hqId));
        return ResponseEntity.ok(res);
    }

    @PutMapping("/alter/pass")
    public ResponseEntity<String> changePassword(@RequestBody String newPass) {
        log.info("[ADMIN] change password req by: {}", newPass);
        return ResponseEntity.ok("");
    }

    @GetMapping("/verified")
    public ResponseEntity<Boolean> authCheck() {
        log.info("[ADMIN] verification req");
        return ResponseEntity.ok(true);
    }

    @Autowired
    private AIService aiService;

    @PostMapping("/ai")
    public ResponseEntity<String> authCheck(@RequestBody Map<String, String> item) {
        String req = item.get("req");
        if (req == null || req.isEmpty()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("req missing");
        String res = aiService.getCriticality(req);
        return ResponseEntity.ok(res);
    }
}
