package com.example.mongodbservice.controllers;

import com.example.mongodbservice.models.TestEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/map/tests")
public class TestsController {
    @GetMapping("")
    public List<TestEntity> getAll() {
        return null;
    }

    @GetMapping("/user/{userId}")
    public List<TestEntity> getAllByUser(@PathVariable Long userId) {
        return null;
    }

    @GetMapping("/{id}")
    public TestEntity getById(@PathVariable Long id) {
        return null;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {

    }

}
