package com.example.telegramapi.services.impl;

import com.example.telegramapi.client.MongoClient;
import com.example.telegramapi.entities.TestEntity;
import com.example.telegramapi.services.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestServiceBean implements TestService {
    private final MongoClient client;

    @Override
    public List<TestEntity> getAll() {
        return client.getAllTests();
    }

    @Override
    public List<TestEntity> getAllByUserId(Long userId) {
        return client.getAllTestsByUserId(userId);
    }

    @Override
    public TestEntity getById(Long id) {
        return client.getTestById(id);
    }

    @Override
    public void update(TestEntity testEntity, Long id) {

    }

    @Override
    public void delete(Long id) {
        client.deleteTest(id);
    }
}
