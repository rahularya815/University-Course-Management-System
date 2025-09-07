package com.Management.university.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {


    @GetMapping("/test")
    public String test() {
        return "This is test";
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello Authenticated";
    }
}
