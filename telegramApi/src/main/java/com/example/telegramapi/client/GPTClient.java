package com.example.telegramapi.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "dictionaryApi", url = "${application.config.dictionary-url}")
public interface GPTClient {
}
