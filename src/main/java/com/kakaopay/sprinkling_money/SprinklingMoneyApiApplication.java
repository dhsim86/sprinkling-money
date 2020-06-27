package com.kakaopay.sprinkling_money;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SprinklingMoneyApiApplication {

    public static void main(String[] args) {
        final SpringApplication application = new SpringApplication(SprinklingMoneyApiApplication.class);
        application.run(args);
    }

}
