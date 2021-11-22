package com.github.manojesus.bugtracker.registration.controller;

import com.github.manojesus.bugtracker.registration.service.RegistrationRequest;
import com.github.manojesus.bugtracker.registration.service.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1.0/register")
@AllArgsConstructor
public class RegistrationController {
    @Autowired
    RegistrationService registrationService;

    @PostMapping
    public String register(@RequestBody RegistrationRequest request){
        return registrationService.register(request);
    }

    @GetMapping(path = "/confirm")
    public String confirmUser(@RequestParam String token){
        return registrationService.confirmToken(token);
    }
}
