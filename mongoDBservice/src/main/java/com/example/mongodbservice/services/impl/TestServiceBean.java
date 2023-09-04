package com.example.mongodbservice.services.impl;

import com.example.mongodbservice.models.TestEntity;
import com.example.mongodbservice.repositories.TestEntitiesRepo;
import com.example.mongodbservice.services.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestServiceBean implements TestService {
    private final TestEntitiesRepo repo;

    @Override
    public List<TestEntity> getAll() {
        return repo.findAll();
    }

    @Override
    public List<TestEntity> getAllByUserId(Long userId) {
        return repo.findByUserId(userId);
    }

    @Override
    public TestEntity getById(String id) {
        return repo.findById(id);
    }

    @Override
    public void update(TestEntity testEntity, String id) {
        TestEntity entity = repo.findById(id);
        entity.setTestDate(testEntity.getTestDate());
        entity.setTests(testEntity.getTests());
        entity.setPassedTimes(testEntity.getPassedTimes());
        repo.save(entity);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
