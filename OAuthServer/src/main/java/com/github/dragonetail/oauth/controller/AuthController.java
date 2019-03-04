package com.github.dragonetail.oauth.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.github.dragonetail.oauth.model.User;
import com.github.dragonetail.oauth.payload.LoginRequest;
import com.github.dragonetail.oauth.payload.SignUpRequest;
import com.github.dragonetail.oauth.payload.UserIdentityAvailability;
import com.github.dragonetail.oauth.repository.UserRepository;
import com.github.dragonetail.oauth.security.JwtTokenProvider;
import com.github.dragonetail.oauth.security.UserPrincipal;
import com.github.dragonetail.util.HttpUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/auth/")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(
            @Valid @RequestBody final LoginRequest loginRequest, final HttpServletRequest request) {
        final String clientIp = HttpUtils.getClientIP(request);
        try {
            final Authentication authentication = this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            final UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            if (log.isInfoEnabled()) {
                log.info("[{}]Login success: {}[{}]", clientIp, userPrincipal.getUsername(),
                        userPrincipal.getId());
            }

            final HttpHeaders headers = new HttpHeaders();
            final String jwtTokenForHeader = this.tokenProvider.buildJwtTokenForHeader(authentication);
            headers.add(JwtTokenProvider.JWT_HEADER_KEY, jwtTokenForHeader);
            headers.add("Access-Control-Expose-Headers", JwtTokenProvider.JWT_HEADER_KEY);
            headers.add("Cache-Control", "no-store");
            final Map<String, String> result = new HashMap<String, String>();
            result.put("result", "OK");
            return new ResponseEntity<Map<String, String>>(result, headers, HttpStatus.OK);
        } catch (final AuthenticationException e) {
            log.warn("[{}]Login failed: {}[{}], {}", clientIp, loginRequest.getUsername(),
                    loginRequest.getPassword(), e.getMessage());
            final Map<String, String> result = new HashMap<String, String>();
            result.put("result", "Failed");
            return new ResponseEntity<Map<String, String>>(result, null, HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<User> registerUser(@Valid @RequestBody final SignUpRequest signUpRequest,
            final HttpServletRequest request) {
        final String clientIp = HttpUtils.getClientIP(request);
        if (this.userRepository.existsByUsername(signUpRequest.getUsername())) {
            log.warn("[{}]Signup username conflicted:  {}", clientIp, signUpRequest.getUsername());
            return new ResponseEntity<User>(new User(), HttpStatus.BAD_REQUEST);
        }

        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getPassword());
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));

        user = this.userRepository.save(user);

        final URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{username}")
                .buildAndExpand(user.getUsername()).toUri();

        if (log.isInfoEnabled()) {
            log.info("[{}]Singup success: {}[{}]", clientIp, user.getUsername(), user.getId());
        }
        return ResponseEntity.created(location).body(user);
    }

    @GetMapping("/checkUsernameAvailability")
    public UserIdentityAvailability checkUsernameAvailability(@RequestParam(value = "username") final String username,
            final HttpServletRequest request) {
        final String clientIp = HttpUtils.getClientIP(request);
        if (log.isInfoEnabled()) {
            log.info("[{}]checkUsernameAvailability: {}[{}]", clientIp, username);
        }
        final Boolean isAvailable = !this.userRepository.existsByUsername(username);
        return new UserIdentityAvailability(isAvailable);
    }
}
