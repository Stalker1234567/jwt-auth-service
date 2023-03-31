package com.robo.harvexsolo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/index")
public class MainController {
    @GetMapping
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Test text");
    }
}