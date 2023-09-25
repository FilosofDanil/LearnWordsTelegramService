package com.example.telegramapi.components.messagesender;

import com.example.telegramapi.services.ObtainTextService;
import com.example.telegramapi.services.bot.TelegramBotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MessageSenderComponent {
    private final TelegramBotService telegramService;

    private final ObtainTextService obtainTextService;


}
