package com.angelangelov.remont_bg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RemontBgApplication {

    public static void main(String[] args) {
        SpringApplication.run(RemontBgApplication.class, args);
    }

}
