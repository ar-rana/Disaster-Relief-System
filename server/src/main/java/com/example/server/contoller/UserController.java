package com.example.server.contoller;

import java.util.Map;

import com.example.server.model.User;
import com.example.server.service.UserService;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> map) {
        log.info("[USER] Login req: {}", map.toString());
        String username = map.get("username");
        String password = map.get("password");
        if (username == null || password == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("username & password required");
        }

        String res = userService.login(username, password);
        if (res == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User does not exist");
        } else {
            return ResponseEntity.ok(res);
        }

        // there will be redirection here DONT RESPOND JSON, DO REDIRECT
    }

    @GetMapping("/logout/{username}")
    public String logout(@PathVariable String username) {
        log.info("[USER] Logout req: {}", username);
        return "Logged Out user!";
    }

    @GetMapping("/verified")
    public ResponseEntity<Boolean> authCheck() {
        log.info("[ADMIN] verification req");
        return ResponseEntity.ok(true);
    }

}
