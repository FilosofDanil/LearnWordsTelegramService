package com.example.telegramapi.client;

import com.example.telegramapi.entities.TestEntity;
import com.example.telegramapi.entities.UserWordList;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "mongoDBservice", url = "${application.config.mongodb-url}")
public interface MongoClient {
    @GetMapping("/translations/user/{userId}")
    List<UserWordList> getAllByUserId(@PathVariable("userId") Long userId);
    @GetMapping("/translations/{id}")
    UserWordList getWordListById(@PathVariable("id") String id);

    @GetMapping("/tests/user/{userId}")
    List<TestEntity> getAllTestsByUserId(@PathVariable("userId") Long userId);

    @GetMapping("/tests")
    List<TestEntity> getAllTests();

    @GetMapping("/tests/{id}")
    TestEntity getTestById(@PathVariable("id") String id);

    @PutMapping("/tests/{id}")
    TestEntity updateTest(@PathVariable("id") String id, @RequestBody TestEntity testEntity);

    @DeleteMapping("/tests/{id}")
    void deleteTest(@PathVariable("id") Long id);

    @PostMapping("/translations")
    UserWordList create(@RequestBody UserWordList wordList);
}
