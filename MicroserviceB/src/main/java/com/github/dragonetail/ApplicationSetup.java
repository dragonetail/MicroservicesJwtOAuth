package com.github.dragonetail;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.dragonetail.service2.model.SkillLevel;
import com.github.dragonetail.service2.model.UserSkill;
import com.github.dragonetail.service2.repository.UserSkillRepository;

@Component
public class ApplicationSetup {
    private static final Logger log = LoggerFactory.getLogger(ApplicationSetup.class);

    @Autowired
    private UserSkillRepository userSkillRepository;

    public void init() {

        if (this.needCleanInit()) {
            this.initDBData();
        }
    }

    private boolean needCleanInit() {
        final Optional<UserSkill> result = this.userSkillRepository.findById(1L);
        if (result.isPresent()) {
            log.info("Database initialization is going to be skipped.");
            return false;
        }
        return true;
    }

    private void initDBData() {

        {
            UserSkill adminSkill = new UserSkill(1L, "Java", "Java Skill", SkillLevel.L1);
            this.userSkillRepository.save(adminSkill);

            adminSkill = new UserSkill(1L, "C++", "C++ Skill", SkillLevel.L2);
            this.userSkillRepository.save(adminSkill);
        }

        {
            final UserSkill kakaxiSkill = new UserSkill(2L, "Go", "Go Skill", SkillLevel.L5);
            this.userSkillRepository.save(kakaxiSkill);
        }

        {
            UserSkill kakaxiSkill = new UserSkill(3L, "Python", "Python Skill", SkillLevel.L5);
            this.userSkillRepository.save(kakaxiSkill);

            kakaxiSkill = new UserSkill(3L, "Testing", "Testing Skill", SkillLevel.L5);
            this.userSkillRepository.save(kakaxiSkill);
        }
    }
}
