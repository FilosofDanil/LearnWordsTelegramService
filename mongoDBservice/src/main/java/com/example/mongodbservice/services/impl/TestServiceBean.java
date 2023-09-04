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
    public TestEntity getById(Long id) {
        return repo.findById(id).get();
    }

    @Override
    public void update(TestEntity testEntity, Long id) {

    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
