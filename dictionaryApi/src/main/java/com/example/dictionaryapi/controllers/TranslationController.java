package com.example.dictionaryapi.controllers;

import com.example.dictionaryapi.services.CheckMessageService;
import com.example.dictionaryapi.services.DetailedTranslateService;
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

    private final DetailedTranslateService detailedTranslateService;

    private final CheckMessageService checkMessageService;

    @GetMapping("/{text}")
    public String translate(@PathVariable String text, @PathParam("langs") String langs) {
        return translationService.translate(text, langs);
    }

    @GetMapping("/detailed/{word}")
    public String detailedTranslate(@PathVariable String word, @PathParam("langs") String langs) {
        return detailedTranslateService.translate(word, langs);
    }

    @GetMapping("/check/{text}")
    public String check(@PathVariable String text){
        return checkMessageService.check(text);
    }
}
