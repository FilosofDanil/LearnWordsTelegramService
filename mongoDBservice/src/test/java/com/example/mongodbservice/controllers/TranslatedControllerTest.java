package com.example.mongodbservice.controllers;

import com.example.mongodbservice.models.TranslatedList;
import com.example.mongodbservice.services.TranslationService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
class TranslatedControllerTest {

    @InjectMocks
    private TranslatedController translatedController;

    @Mock
    private TranslationService translationService;

    @Test
    void getAllTranslations_Success() {
        List<TranslatedList> translatedLists = Arrays.asList(TranslatedList.builder().build(), TranslatedList.builder().build());
        Mockito.when(translationService.getAll()).thenReturn(translatedLists);

        List<TranslatedList> result = translatedController.getAll();

        assertEquals(translatedLists, result);
    }

    @Test
    void getTranslationById_Success() {
        String translationId = "1";
        TranslatedList translatedList = TranslatedList.builder().build();
        Mockito.when(translationService.getById(translationId)).thenReturn(translatedList);

        TranslatedList result = translatedController.getById(translationId);

        assertEquals(translatedList, result);
    }

    @Test
    void getAllTranslationsByUser_Success() {
        Long userId = 1L;
        List<TranslatedList> translatedLists = Arrays.asList(TranslatedList.builder().build(), TranslatedList.builder().build());
        Mockito.when(translationService.getAllByUser(userId)).thenReturn(translatedLists);

        List<TranslatedList> result = translatedController.getAllByUserId(userId);

        assertEquals(translatedLists, result);
    }

    @Test
    void createTranslation_Success() {
        TranslatedList translatedList = TranslatedList.builder().build();
        Mockito.when(translationService.create(Mockito.any(TranslatedList.class))).thenReturn(translatedList);

        TranslatedList result = translatedController.create(translatedList);

        assertEquals(translatedList, result);
    }

    @Test
    void deleteTranslation_Success() {
        String translationId = "1";
        translatedController.delete(translationId);

        Mockito.verify(translationService, Mockito.times(1)).delete(translationId);
    }

    @Test
    void updateTranslation_Success() {
        String translationId = "1";
        TranslatedList translatedList = TranslatedList.builder().build();
        translatedController.update(translationId, translatedList);

        Mockito.verify(translationService, Mockito.times(1)).update(translatedList, translationId);
    }
}