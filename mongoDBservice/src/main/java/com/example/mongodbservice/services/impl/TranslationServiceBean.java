package com.example.mongodbservice.services.impl;

import com.example.mongodbservice.components.RandomComponent;
import com.example.mongodbservice.enums.TestFormat;
import com.example.mongodbservice.models.Test;
import com.example.mongodbservice.models.TestEntity;
import com.example.mongodbservice.models.TranslatedList;
import com.example.mongodbservice.repositories.TestEntitiesRepo;
import com.example.mongodbservice.repositories.TranslatedListRepo;
import com.example.mongodbservice.services.TranslationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class TranslationServiceBean implements TranslationService {
    private final TranslatedListRepo listRepo;

    private final TestEntitiesRepo testEntitiesRepo;

    private final RandomComponent randomComponent;

    @Override
    public List<TranslatedList> getAll() {
        return listRepo.findAll();
    }

    @Override
    public List<TranslatedList> getAllByUser(Long userId) {
        return listRepo.getByUserId(userId);
    }

    @Override
    public TranslatedList getById(String id) {
        return listRepo.findById(id);
    }

    @Override
    public TranslatedList create(TranslatedList translatedList) {
        TranslatedList savedTranslatedList = listRepo.save(translatedList);
        createTest(savedTranslatedList);
        return savedTranslatedList;
    }

    @Override
    public void delete(String id) {
        listRepo.deleteById(id);
    }

    @Override
    public void update(TranslatedList translatedList, String id) {
        listRepo.updateByUserId(id, translatedList);
    }

    private void createTest(TranslatedList translatedList) {
        Map<String, TestFormat> tests = randomComponent.generateTests(translatedList);
        Date today = new Date();
        List<Test> testList = new ArrayList<>();
        translatedList.getTranslations().keySet().stream().forEach(row -> {
            testList.add(Test.builder()
                    .answer(null)
                    .correct(false)
                    .testFormat(tests.get(row))
                    .correctAnswers(List.of(row, row.toUpperCase(Locale.ROOT), row.toLowerCase(Locale.ROOT)))
                    .build());
        });
        TestEntity testEntity = TestEntity.builder()
                .tests(testList)
                .testDate(today)
                .userId(translatedList.getUserId())
                .passedTimes(0)
                .listId(translatedList.getId())
                .firstNotify(false)
                .notified(false)
                .build();
        testEntitiesRepo.save(testEntity);
    }
}
