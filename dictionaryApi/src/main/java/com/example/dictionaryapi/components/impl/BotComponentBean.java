package com.example.dictionaryapi.components.impl;

import com.example.dictionaryapi.components.BotComponent;
import com.example.dictionaryapi.entities.ChatGPTRequest;
import com.example.dictionaryapi.entities.ChatGPTResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class BotComponentBean implements BotComponent {
    @Value("${openai.model}")
    private String model;

    @Value(("${openai.api.url}"))
    private String apiURL;

    @Autowired
    private RestTemplate template;

    @Override
    public String getResponseMessage(String message) {
        ChatGPTRequest request=new ChatGPTRequest(model, message);
        ChatGPTResponse chatGptResponse = template.postForObject(apiURL, request, ChatGPTResponse.class);
        assert chatGptResponse != null;
        return chatGptResponse.getChoices().get(0).getMessage().getContent();
    }
}
