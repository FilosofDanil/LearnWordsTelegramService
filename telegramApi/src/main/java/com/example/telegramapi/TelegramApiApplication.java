package com.example.telegramapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TelegramApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TelegramApiApplication.class, args);
    }

}
