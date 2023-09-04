package com.example.telegramapi.services.impl;

import com.example.telegramapi.entities.TestEntity;
import com.example.telegramapi.services.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestServiceBean implements TestService {
    @Override
    public List<TestEntity> getAll() {
        return null;
    }

    @Override
    public List<TestEntity> getAllByUserId(Long userId) {
        return null;
    }

    @Override
    public TestEntity getById(Long id) {
        return null;
    }

    @Override
    public void update(TestEntity testEntity, Long id) {

    }

    @Override
    public void delete(Long id) {

    }
}
