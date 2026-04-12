package com.restoran.menuservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = "com.restoran.menuservice")
@EnableJpaRepositories(basePackages = "com.restoran.menuservice.infrastructure.persistence")
@EntityScan(basePackages = "com.restoran.menuservice.infrastructure.persistence")
public class MenuServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(MenuServiceApplication.class, args);
    }
}
