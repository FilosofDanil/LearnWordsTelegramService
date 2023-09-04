package com.example.telegramapi.services;

import com.example.telegramapi.entities.TestEntity;

import java.util.List;

public interface TestService {
    List<TestEntity> getAll();

    List<TestEntity> getAllByUserId(Long userId);

    TestEntity getById(Long id);

    void update(TestEntity testEntity, Long id);

    void delete(Long id);
}
