package com.example.mongodbservice.services;

import com.example.mongodbservice.models.TestEntity;

import java.util.List;

public interface TestService {
    List<TestEntity> getAll();

    List<TestEntity> getAllByUserId(Long userId);

    TestEntity getById(String id);

    void update(TestEntity testEntity, String id);

    void delete(String id);
}
