package com.example.telegramapi.components.sup.intermidiate_operations;

import com.example.telegramapi.services.ObtainTextService;
import com.example.telegramapi.services.SessionService;
import com.example.telegramapi.services.bot.TelegramBotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SendHelpMessage {
    private final TelegramBotService telegramService;

    private final ObtainTextService obtainTextService;

    public void sendHelpMessage(Long chatId,String text, String lang){
        telegramService.sendMessage(chatId, obtainTextService.read(text, lang));
    }
}
