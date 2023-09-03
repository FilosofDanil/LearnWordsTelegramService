package com.example.telegramapi.client;

import com.example.telegramapi.entities.UserWordList;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "mongoDBservice", url = "${application.config.mongodb-url}")
public interface MongoClient {
    @GetMapping("/translations/user/{userId}")
    List<UserWordList> getAllByUserId(@PathVariable("userId") Long userId);

    @PostMapping("/translations")
    UserWordList create(@RequestBody UserWordList wordList);
}
