package com.example.mongodbservice.controllers;

import com.example.mongodbservice.models.TestEntity;
import com.example.mongodbservice.services.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/map/tests")
public class TestsController {
    private final TestService testService;
    @GetMapping("")
    public List<TestEntity> getAll() {
        return testService.getAll();
    }

    @GetMapping("/user/{userId}")
    public List<TestEntity> getAllByUser(@PathVariable Long userId) {
        return testService.getAllByUserId(userId);
    }

    @GetMapping("/{id}")
    public TestEntity getById(@PathVariable String id) {
        return testService.getById(id);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable String id, @RequestBody TestEntity testEntity) {
        testService.update(testEntity, id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        testService.delete(id);
    }

}
