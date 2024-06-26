package com.bootx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author black
 */
@SpringBootApplication
@EnableScheduling
public class BootxAiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootxAiApplication.class, args);
    }

}
