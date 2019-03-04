package com.github.dragonetail;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.dragonetail.service1.model.UserDetails;
import com.github.dragonetail.service1.repository.UserDetailsRepository;

@Component
public class ApplicationSetup {
    private static final Logger log = LoggerFactory.getLogger(ApplicationSetup.class);

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    public void init() {

        if (this.needCleanInit()) {
            this.initDBData();
        }
    }

    private boolean needCleanInit() {
        final Optional<UserDetails> result = this.userDetailsRepository.findById(1L);
        if (result.isPresent()) {
            log.info("Database initialization is going to be skipped.");
            return false;
        }
        return true;
    }

    private void initDBData() {

        final UserDetails admin = new UserDetails(1L, "admin", "admin@dragonetail.github.com",
                "New York", "US");
        this.userDetailsRepository.save(admin);

        final UserDetails kakaxi = new UserDetails(1L, "kakaxi", "kakaxi@dragonetail.github.com",
                "New York", "US");
        this.userDetailsRepository
                .save(kakaxi);
        final UserDetails userDetails = new UserDetails(3L, "user", "user@dragonetail.github.com",
                "New York", "US");
        this.userDetailsRepository.save(userDetails);
    }
}
