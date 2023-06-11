package org.quentin.security.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.quentin.security.domain.vo.AuthenticationRequest;
import org.quentin.security.domain.vo.AuthenticationResponse;
import org.quentin.security.domain.vo.RegisterRequest;
import org.quentin.security.domain.vo.UserDetail;
import org.quentin.security.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthcController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthcController.class);

    private final AuthService authenticate;

    public AuthcController(AuthService authenticate) {
        this.authenticate = authenticate;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authenticate.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticate.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        authenticate.refreshToken(request, response);
    }

    @RequestMapping("/success")
    public String success() {
        return "success";
    }

    @RequestMapping("/token")
    public Map<String, String> token(HttpSession session) {
        return Collections.singletonMap("token", session.getId());
    }

}
