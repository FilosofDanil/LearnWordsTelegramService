package com.example.mongodbservice.repositories;

import com.example.mongodbservice.models.TestEntity;
import com.example.mongodbservice.models.TranslatedList;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TestEntitiesRepo extends MongoRepository<TestEntity, Long> {
    List<TestEntity> findByUserId(Long userId);
}
