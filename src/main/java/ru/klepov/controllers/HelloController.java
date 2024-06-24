package ru.klepov.controllers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {
    @GetMapping()
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello, World!");
    }
    @GetMapping("/who")
    public ResponseEntity<String> who(@RequestHeader(HttpHeaders.USER_AGENT) String agent) {
        return ResponseEntity.ok("You are "+agent);
    }
}
