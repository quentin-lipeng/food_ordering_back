package org.quentin.security.controller;

import org.quentin.security.pojo.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authc")
public class AuthcController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthcController.class);

    @RequestMapping("/user")
    public String verify(@RequestBody Account account) {
        LOGGER.info("user = {}", account);

        return "ok";
    }
}
