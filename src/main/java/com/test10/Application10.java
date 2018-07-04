package com.test10;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class Application10
{
    public static void main(String[] args) {
        SpringApplication.run(Application10.class, args);
    }
}
