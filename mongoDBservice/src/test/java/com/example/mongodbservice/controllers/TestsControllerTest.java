package com.example.mongodbservice.controllers;

import com.example.mongodbservice.models.TestEntity;
import com.example.mongodbservice.repositories.TestEntitiesRepo;
import com.example.mongodbservice.services.TestService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(MockitoExtension.class)
class TestsControllerTest {

    @InjectMocks
    private TestsController testsController;

    @Mock
    private TestService testService;

    @Mock
    private TestEntitiesRepo testEntitiesRepo;

    @Test
    void getAllTests_Success() {
        List<TestEntity> testEntities = Arrays.asList(TestEntity.builder().build(), TestEntity.builder().build());
        Mockito.when(testService.getAll()).thenReturn(testEntities);

        List<TestEntity> result = testsController.getAll();

        assertEquals(testEntities, result);
    }

    @Test
    void getAllTestsByUser_Success() {
        Long userId = 1L;
        List<TestEntity> testEntities = Arrays.asList(TestEntity.builder().build(), TestEntity.builder().build());
        Mockito.when(testService.getAllByUserId(userId)).thenReturn(testEntities);

        List<TestEntity> result = testsController.getAllByUser(userId);

        assertEquals(testEntities, result);
    }

    @Test
    void getTestById_Success() {
        String testId = "1";
        TestEntity testEntity = TestEntity.builder().build();
        Mockito.when(testService.getById(testId)).thenReturn(testEntity);

        TestEntity result = testsController.getById(testId);

        assertEquals(testEntity, result);
    }

    @Test
    void getTestByWordListId_Success() {
        String listId = "1";
        TestEntity testEntity = TestEntity.builder().build();
        Mockito.when(testService.getByWordListId(listId)).thenReturn(testEntity);

        TestEntity result = testsController.getByWordListId(listId);

        assertEquals(testEntity, result);
    }

    @Test
    void updateTest_Success() {
        String testId = "1";
        TestEntity testEntity = TestEntity.builder().build();
        testsController.update(testId, testEntity);

        Mockito.verify(testService, Mockito.times(1)).update(testEntity, testId);
    }

    @Test
    void deleteTest_Success() {
        String testId = "1";
        testsController.delete(testId);

        Mockito.verify(testService, Mockito.times(1)).delete(testId);
    }
}