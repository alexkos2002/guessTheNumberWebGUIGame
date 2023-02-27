package com.example.guessthenumberwebguigame.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GuessNumberRestController {

    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello, world!";
    }

}
