package com.github.dragonetail;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.github.dragonetail.oauth.model.User;
import com.github.dragonetail.oauth.repository.UserRepository;

@Component
public class ApplicationSetup {
    private static final Logger log = LoggerFactory.getLogger(ApplicationSetup.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void init() {

        if (this.needCleanInit()) {
            this.initDBData();
        }
    }

    private boolean needCleanInit() {
        final Optional<User> result = this.userRepository.findByUsername("admin");
        if (result.isPresent()) {
            log.info("Database initialization is going to be skipped.");
            return false;
        }
        return true;
    }

    private void initDBData() {
        final User admin = new User("admin", this.passwordEncoder.encode("USPassw@rd!"));
        this.userRepository.save(admin);

        final User kakaxi = new User("kakaxi", this.passwordEncoder.encode("USPassw@rd!"));
        this.userRepository.save(kakaxi);

        final User user = new User("user", this.passwordEncoder.encode("USPassw@rd!"));
        this.userRepository.save(user);
    }
}
