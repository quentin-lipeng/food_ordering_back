package org.quentin.security.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/root")
public class RootController {
    @RequestMapping("")
    public String hello(){
        return "hello";
    }
}
