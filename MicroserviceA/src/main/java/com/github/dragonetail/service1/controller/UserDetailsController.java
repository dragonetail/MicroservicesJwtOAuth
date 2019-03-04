package com.github.dragonetail.service1.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.github.dragonetail.service1.model.UserDetails;
import com.github.dragonetail.service1.repository.UserDetailsRepository;
import com.github.dragonetail.service1.security.UserPrincipal;
import com.github.dragonetail.service2.model.UserSkill;

@RestController
public class UserDetailsController {

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/candidate/me")
    public ResponseEntity<UserDetails> getUserDetails(@AuthenticationPrincipal final UserPrincipal currentUser) {
        final Optional<UserDetails> userDetailsResult = this.userDetailsRepository.findById(currentUser.getId());
        if (!userDetailsResult.isPresent()) {
            return new ResponseEntity<UserDetails>(new UserDetails(), HttpStatus.NOT_FOUND);
        }

        final UserDetails userDetails = userDetailsResult.get();

        final ResponseEntity<List<UserSkill>> responseEntity = this.restTemplate
                .exchange("http://localhost:5002/skills/candidate/me", HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<UserSkill>>() {
                        });
        userDetails.setSkills(responseEntity.getBody());

        return ResponseEntity.ok(userDetails);
    }
}
