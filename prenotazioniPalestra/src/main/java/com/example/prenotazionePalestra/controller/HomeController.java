package com.example.prenotazionePalestra.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;

@RestController
public class HomeController {

    @RequestMapping("/")
    public ResponseEntity<String> home(Authentication authentication){
        final String body = "Hello"+ authentication.getName();
        return ResponseEntity.ok(body);
    }
}
