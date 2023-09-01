package com.example.mongodbservice.services.impl;

import com.example.mongodbservice.models.TranslatedList;
import com.example.mongodbservice.repositories.TranslatedListRepo;
import com.example.mongodbservice.services.TranslationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TranslationServiceBean implements TranslationService {
    private final TranslatedListRepo listRepo;

    @Override
    public List<TranslatedList> getAll() {
        return listRepo.findAll();
    }

    @Override
    public List<TranslatedList> getAllByUser(Long userId) {
        return listRepo.getByUserId(userId);
    }

    @Override
    public TranslatedList getById(Long id) {
        return listRepo.findById(id).get();
    }

    @Override
    public TranslatedList create(TranslatedList translatedList) {
        return listRepo.save(translatedList);
    }

    @Override
    public void delete(Long id) {
        listRepo.deleteById(id);
    }

    @Override
    public void update(TranslatedList translatedList, Long id) {
        listRepo.updateByUserId(id, translatedList);
    }
}
