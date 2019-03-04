package com.github.dragonetail.service2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.dragonetail.service2.model.UserSkill;
import com.github.dragonetail.service2.repository.UserSkillRepository;
import com.github.dragonetail.service2.security.UserPrincipal;

@RestController
public class UserSkillController {

    @Autowired
    private UserSkillRepository userSkillRepository;

    @GetMapping("/skills/candidate/me")
    public ResponseEntity<List<UserSkill>> getUserDetails(@AuthenticationPrincipal final UserPrincipal currentUser) {
        final List<UserSkill> userSkills = this.userSkillRepository.findAllByUserId(currentUser.getId());

        return ResponseEntity.ok(userSkills);
    }

}
