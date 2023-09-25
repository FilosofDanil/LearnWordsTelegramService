package com.example.mongodbservice.services.impl;

import com.example.mongodbservice.components.RandomComponent;
import com.example.mongodbservice.enums.TestFormat;
import com.example.mongodbservice.models.MapContainer;
import com.example.mongodbservice.models.Test;
import com.example.mongodbservice.models.TestEntity;
import com.example.mongodbservice.models.TranslatedList;
import com.example.mongodbservice.repositories.TestEntitiesRepo;
import com.example.mongodbservice.services.TestService;
import com.example.mongodbservice.services.TranslationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TestServiceBean implements TestService {
    private final TestEntitiesRepo repo;

    private final TranslationService translationService;

    private final RandomComponent randomComponent;

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
    public TestEntity getByWordListId(String id) {
        return repo.findByListId(id);
    }

    @Override
    public void update(TestEntity testEntity, String id) {
        TestEntity entity = repo.findById(id);
        Integer passedTimes = testEntity.getPassedTimes();
        if(!Objects.equals(entity.getPassedTimes(), passedTimes)){
            entity.setTests(randomizeTests(entity.getListId()));
            if(passedTimes<7){
                entity.setTestDate(getFurtherTestDate(passedTimes));
            } else {
                repo.deleteById(id);
            }
        } else{
            entity.setTests(testEntity.getTests());
        }
        entity.setFirstNotify(testEntity.getFirstNotify());
        entity.setNotified(testEntity.getNotified());
        entity.setPassedTimes(testEntity.getPassedTimes());
        entity.setTestReady(testEntity.getTestReady());
        repo.save(entity);
    }

    private Date getFurtherTestDate(Integer passedTimes){
        MapContainer mapContainer = new MapContainer();
        LocalDateTime dateTime = mapContainer.getMap().get(passedTimes);
        return Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());

    }

    @Override
    public void delete(String id) {
        repo.deleteById(id);
    }

    private List<Test> randomizeTests(String translatedListId){
        TranslatedList translatedList = translationService.getById(translatedListId);
        Map<String, TestFormat> tests = randomComponent.generateTests(translatedList);
        List<Test> testList = new ArrayList<>();
        translatedList.getTranslations().keySet().stream().forEach(row -> {
            testList.add(Test.builder()
                    .answer(null)
                    .correct(false)
                    .testFormat(tests.get(row))
                    .correctAnswers(List.of(row, row.toUpperCase(Locale.ROOT), row.toLowerCase(Locale.ROOT)))
                    .build());
        });
        return testList;
    }
}
