package com.example.mongodbservice.services.impl;

import com.example.mongodbservice.models.MapContainer;
import com.example.mongodbservice.models.TestEntity;
import com.example.mongodbservice.repositories.TestEntitiesRepo;
import com.example.mongodbservice.services.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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
    public TestEntity getByWordListId(String id) {
        return repo.findByListId(id);
    }

    @Override
    public void update(TestEntity testEntity, String id) {
        TestEntity entity = repo.findById(id);
        Integer passedTimes = testEntity.getPassedTimes();
        if(!Objects.equals(entity.getPassedTimes(), passedTimes)){
            if(passedTimes<7){
                System.out.println(getFurtherTestDate(passedTimes));
                entity.setTestDate(getFurtherTestDate(passedTimes));
            } else {
                repo.deleteById(id);
            }
        }
        entity.setTests(testEntity.getTests());
        entity.setPassedTimes(testEntity.getPassedTimes());
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
}
