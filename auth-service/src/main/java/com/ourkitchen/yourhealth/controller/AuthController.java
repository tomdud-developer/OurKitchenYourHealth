package com.ourkitchen.yourhealth.controller;

import com.ourkitchen.yourhealth.dto.RegisterRequest;
import com.ourkitchen.yourhealth.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final SecurityService securityService;

    @GetMapping("test")
    String testController() {
        return "test complete";
    }

    @PostMapping("register")
    public void registerNewUser(@RequestBody RegisterRequest registerRequest) {
        securityService.registerUser(registerRequest);
    }

}
