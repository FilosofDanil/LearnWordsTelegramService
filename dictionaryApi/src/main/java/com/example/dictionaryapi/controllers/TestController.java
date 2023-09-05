package com.example.dictionaryapi.controllers;

import com.example.dictionaryapi.services.FormTestService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/dictionary/test")
public class TestController {
    private final FormTestService testService;

    @GetMapping("/{answer}")
    public List<String> formTest(@PathVariable String answer, @PathParam("lang") String lang){
        return testService.createTest(answer, lang);
    }
}
