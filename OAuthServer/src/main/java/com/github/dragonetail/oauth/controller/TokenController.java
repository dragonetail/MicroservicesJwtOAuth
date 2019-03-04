package com.github.dragonetail.oauth.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.dragonetail.oauth.repository.UserRepository;
import com.github.dragonetail.oauth.security.JwtTokenProvider;

@RestController
@RequestMapping("/token/")
public class TokenController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @PostMapping("/refresh")
    public ResponseEntity<Map<String, String>> refreshToken() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        final HttpHeaders headers = new HttpHeaders();
        final String jwtTokenForHeader = this.tokenProvider.buildJwtTokenForHeader(authentication);
        headers.add(JwtTokenProvider.JWT_HEADER_KEY, jwtTokenForHeader);
        headers.add("Access-Control-Expose-Headers", JwtTokenProvider.JWT_HEADER_KEY);
        headers.add("Cache-Control", "no-store");

        final Map<String, String> result = new HashMap<String, String>();
        result.put("result", "OK");
        return new ResponseEntity<Map<String, String>>(result, headers, HttpStatus.OK);
    }
}
