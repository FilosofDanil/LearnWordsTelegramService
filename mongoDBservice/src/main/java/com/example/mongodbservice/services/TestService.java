package com.example.mongodbservice.services;

import com.example.mongodbservice.models.TestEntity;

import java.util.List;

public interface TestService {
    List<TestEntity> getAll();

    List<TestEntity> getAllByUserId(Long userId);

    TestEntity getById(Long id);

    void update(TestEntity testEntity, Long id);

    void delete(Long id);
}
