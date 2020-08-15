package com.cz.zfy.vhr.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    @GetMapping("/employee/basic/hello")
    public String hello2(){
        return "/employee/basic/hello";
    }
    @GetMapping("/employee/advcancx/hello")
    public String hello3(){
        return "/employee/basic/hello";
    }
}
