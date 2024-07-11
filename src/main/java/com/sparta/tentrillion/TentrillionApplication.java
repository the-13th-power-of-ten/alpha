package com.sparta.tentrillion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class TentrillionApplication {

    public static void main(String[] args) {
        SpringApplication.run(TentrillionApplication.class, args);
    }

}