package com.example.telegramapi.components.additions;

import com.example.telegramapi.entities.UserRequest;
import com.example.telegramapi.services.GPTInterogativeService;
import com.example.telegramapi.services.MongoDBService;
import com.example.telegramapi.services.ObtainTextService;
import com.example.telegramapi.services.SessionService;
import com.example.telegramapi.services.bot.TelegramBotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RandomWordListComponent {
    private final ObtainTextService obtainTextService;

    private final SessionService sessionService;

    private final TelegramBotService telegramService;

    private final GPTInterogativeService gptService;

    public void sendRandomList(UserRequest request) {


    }

    private void saveUserData(List<String> wordList){


    }
}
