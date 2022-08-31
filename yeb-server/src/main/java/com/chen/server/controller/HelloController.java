package com.chen.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: blkcor
 * @DATE: 2022/3/25  23:41
 * @PROJECT_NAME: yeb
 * @since: jdk1.8
 */
@RestController
public class HelloController {
    @GetMapping("/hello")
    public String hello() {
        return "hello yeb!";
    }

    @GetMapping("/employee/basic/hello")
    public String ebh(){
        return "basic-information";
    }

    @GetMapping("/employee/advanced/hello")
    public String eah(){
        return "advance-information";
    }
}
