package com.example.mongodbservice.repositories;

import com.example.mongodbservice.models.TranslatedList;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TranslatedListRepo extends MongoRepository<TranslatedList, Long> {
    List<TranslatedList> getByUserId(Long userId);

    @Query("{'userId': ?0}")
    void updateByUserId(Long userId, TranslatedList updatedTranslatedList);
}
