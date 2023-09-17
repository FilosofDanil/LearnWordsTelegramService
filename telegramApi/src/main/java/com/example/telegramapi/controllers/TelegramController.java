package com.example.telegramapi.controllers;

import com.example.telegramapi.dispatcher.Dispatcher;
import com.example.telegramapi.entities.telegram.UserRequest;
import com.example.telegramapi.entities.telegram.UserSession;
import com.example.telegramapi.services.SessionService;
import com.example.telegramapi.configs.BotConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Controller
@RequiredArgsConstructor
public class TelegramController extends TelegramLongPollingBot {

    private final Dispatcher dispatcher;

    private final BotConfig config;

    private final SessionService sessionService;


    @Override
    public String getBotUsername() {
        return config.getName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        Long chatId;
        if (update.hasCallbackQuery()) {
            chatId = update.getCallbackQuery().getMessage().getChatId();
        } else {
            chatId = update.getMessage().getChatId();
        }
        UserSession session = sessionService.getSession(chatId);
        UserRequest request = UserRequest
                .builder()
                .update(update)
                .chatId(chatId)
                .build();
        sessionService.checkUseData(session, request);
        sessionService.saveSession(request.getChatId(), session);
        dispatcher.dispatch(request);
    }

    public void sendMessage(SendMessage message) {
        if (message != null) {
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}

