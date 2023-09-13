package com.example.telegramapi.services;

import com.example.telegramapi.entities.TestEntity;

import java.util.List;

public interface TestService {
    List<TestEntity> getAll();

    List<TestEntity> getAllByUserId(Long userId);

    TestEntity getById(String id);

    TestEntity getByWordListId(String id);

    void update(TestEntity testEntity, String id);

    void delete(Long id);
}
