package org.quentin.security.controller;

import org.quentin.security.domain.vo.CommonResponse;
import org.quentin.security.domain.vo.UserResponse;
import org.quentin.security.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/info")
    public ResponseEntity<CommonResponse<UserResponse>> userInfo(
            @RequestHeader("Authorization") String bearerToken) {
        String token = bearerToken.substring(7);
        UserResponse userFromToken = userService.fetchUserWithInfo(token);
        return ResponseEntity.ok(new CommonResponse<>("user info", userFromToken));
    }
}
