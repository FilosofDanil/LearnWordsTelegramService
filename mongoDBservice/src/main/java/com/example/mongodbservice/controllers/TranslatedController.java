package com.example.mongodbservice.controllers;

import com.example.mongodbservice.models.TranslatedList;
import com.example.mongodbservice.services.TranslationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/map")
public class TranslatedController {
    private final TranslationService translationService;

    @GetMapping("")
    public List<TranslatedList> getAll() {
        return translationService.getAll();
    }

    @GetMapping("/{id}")
    public TranslatedList getById(@PathVariable("id") Long id) {
        return translationService.getById(id);
    }

    @GetMapping("/user/{userId}")
    public List<TranslatedList> getAllByUserId(@PathVariable("userId") Long userId) {
        return translationService.getAllByUser(userId);
    }

    @PostMapping("")
    public void create(@RequestBody TranslatedList list) {
        translationService.create(list);

    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        translationService.delete(id);

    }

    @PutMapping("/{id}")
    public void update(@PathVariable("id") Long id, @RequestBody TranslatedList list) {
        translationService.update(list, id);

    }
}
