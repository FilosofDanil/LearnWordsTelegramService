package com.example.telegramapi.client;

import com.example.telegramapi.entities.GPTResponseEntity;
import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "dictionaryApi", url = "${application.config.dictionary-url}")
public interface GPTClient {
    @GetMapping("/translate/{text}")
    GPTResponseEntity getMessage(@PathVariable("text") String text, @Param("langs") String langs);
}
