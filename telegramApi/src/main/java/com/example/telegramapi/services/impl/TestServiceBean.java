package com.example.telegramapi.services.impl;

import com.example.telegramapi.client.MongoClient;
import com.example.telegramapi.entities.tests_data.TestEntity;
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
    public TestEntity getById(String id) {
        return client.getTestById(id);
    }

    @Override
    public TestEntity getByWordListId(String id) {
        return client.getTestByWordListId(id);
    }

    @Override
    public void update(TestEntity testEntity, String id) {
        client.updateTest(id, testEntity);
    }

    @Override
    public void delete(Long id) {
        client.deleteTest(id);
    }
}
