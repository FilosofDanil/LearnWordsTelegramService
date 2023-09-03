package com.example.mongodbservice.repositories;

import com.example.mongodbservice.models.TranslatedList;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TestEntitiesRepo extends MongoRepository<TranslatedList, Long> {
}
