package com.example.telegramapi.client;

import com.example.telegramapi.entities.tests_data.TestEntity;
import com.example.telegramapi.entities.tests_data.UserWordList;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "mongoDBservice", url = "${application.config.mongodb-url}")
public interface MongoClient {
    @GetMapping("/translations/user/{userId}")
    List<UserWordList> getAllByUserId(@PathVariable("userId") Long userId);

    @GetMapping("/translations/{id}")
    UserWordList getWordListById(@PathVariable("id") String id);

    @GetMapping("/translations")
    List<UserWordList> getAllTranslations();

    @DeleteMapping("/translations/{id}")
    UserWordList deleteWordList(@PathVariable("id") String id);

    @PutMapping("/translations/{id}")
    TestEntity updateWordList(@PathVariable("id") String id, @RequestBody UserWordList wordList);

    @GetMapping("/tests/user/{userId}")
    List<TestEntity> getAllTestsByUserId(@PathVariable("userId") Long userId);

    @GetMapping("/tests")
    List<TestEntity> getAllTests();

    @GetMapping("/tests/{id}")
    TestEntity getTestById(@PathVariable("id") String id);

    @GetMapping("/tests/list/{listId}")
    TestEntity getTestByWordListId(@PathVariable("listId") String listId);

    @PutMapping("/tests/{id}")
    TestEntity updateTest(@PathVariable("id") String id, @RequestBody TestEntity testEntity);

    @DeleteMapping("/tests/{id}")
    void deleteTest(@PathVariable("id") Long id);

    @PostMapping("/translations")
    UserWordList create(@RequestBody UserWordList wordList);
}
