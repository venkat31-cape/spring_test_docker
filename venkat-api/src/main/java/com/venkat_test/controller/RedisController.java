package com.venkat_test.controller;

import com.venkat_test.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/redis")
public class RedisController {

    private final RedisService redisService;

    @Autowired
    public RedisController(RedisService redisService) {
        this.redisService = redisService;
    }

    @GetMapping("/ping")
    public String ping() {
        return redisService.ping();
    }

    @PostMapping("/set")
    public String setValue(@RequestParam String key, @RequestBody String value) {
        redisService.setValue(key, value);
        return "Value set";
    }

    @GetMapping("/get")
    public String getValue(@RequestParam String key) {
        return redisService.getValue(key);
    }
}