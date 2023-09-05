package com.example.telegramapi.components.additions;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class LanguageComponent {
    private Map<String, String> langMap;

    public LanguageComponent() {
        langMap = new HashMap<>();
        langMap.put("en", "English");
        langMap.put("uk", "Ukrainian");
        langMap.put("de", "German");
        langMap.put("fr", "French");
        langMap.put("es", "Spanish");
    }

    public String getLang(String lang) {
        return langMap.get(lang);
    }
}
