package com.example.mongodbservice.repositories;

import com.example.mongodbservice.models.TestEntity;
import com.example.mongodbservice.models.TranslatedList;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TestEntitiesRepo extends MongoRepository<TestEntity, Long> {
}
