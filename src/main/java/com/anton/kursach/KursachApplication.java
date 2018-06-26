package com.anton.kursach;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(basePackages = {"com.anton.kursach"})
@EnableJpaRepositories(basePackages = {"com.anton.kursach.repository"})
@EntityScan(value = "com.anton.kursach.model")
@PropertySource(value = {"classpath:sendgrid.properties"})
@EnableScheduling
public class KursachApplication {

    public static void main(String[] args) {
        SpringApplication.run(KursachApplication.class, args);
    }

}
