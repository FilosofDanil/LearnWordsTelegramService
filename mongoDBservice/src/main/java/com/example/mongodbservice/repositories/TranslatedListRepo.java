package com.example.mongodbservice.repositories;

import com.example.mongodbservice.models.TranslatedList;
import com.mongodb.client.MongoDatabase;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TranslatedListRepo extends MongoRepository<TranslatedList, Long> {
}
