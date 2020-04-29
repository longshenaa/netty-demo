package com.example.nettydemo;

import com.example.nettydemo.redis.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @Autowired
    private RedisService redisService;
    @GetMapping(value = "/setTest")
    public String setTest() {
        redisService.setObject("hello", "hello", 100);
        return "hello";
    }
    @GetMapping(value = "/getTest")
    public String getTest(String key) {
        return redisService.getStr(key);
    }
}
