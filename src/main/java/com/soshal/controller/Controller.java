package com.soshal.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping("/api/resource")
    public String getResource(@RequestParam(required = true) String id) {
        return "Resource with ID: " + id;
    }
}