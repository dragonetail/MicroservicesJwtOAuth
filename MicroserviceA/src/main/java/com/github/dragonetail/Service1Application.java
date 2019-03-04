package com.github.dragonetail;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

@SpringBootApplication
@EntityScan(basePackages = { "com.github.dragonetail" }, basePackageClasses = {
        Jsr310JpaConverters.class
})
public class Service1Application {
    public static void main(final String[] args) {
        SpringApplication.run(Service1Application.class, args);
    }

    @Bean
    CommandLineRunner processApplicationSetup(final ApplicationSetup applicationSetup) {
        return (args) -> {
            applicationSetup.init();
        };
    }
}
