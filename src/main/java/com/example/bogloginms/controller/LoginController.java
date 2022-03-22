package com.example.bogloginms.controller;

import com.example.bogloginms.dto.LoginRequestDTO;
import com.example.bogloginms.feign.feignDto.UserDTO;
import com.example.bogloginms.service.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class LoginController {

    private LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        return ResponseEntity.ok().body(loginService.login(loginRequestDTO));
    }

    @GetMapping("/validateToken")
    public ResponseEntity<String> validateToken(@RequestParam String token) {
        return ResponseEntity.ok().body(loginService.validateToken(token));
    }

}
