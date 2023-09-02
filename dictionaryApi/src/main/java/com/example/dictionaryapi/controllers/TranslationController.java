package com.example.dictionaryapi.controllers;

import com.example.dictionaryapi.entities.TranslatedListModel;
import com.example.dictionaryapi.services.TranslationService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/dictionary/translate")
public class TranslationController {
    private final TranslationService translationService;

    @GetMapping("/{text}")
    public TranslatedListModel translate(@PathVariable String text, @PathParam("langs") String langs){
        return translationService.translate(text, langs);
    }
}
