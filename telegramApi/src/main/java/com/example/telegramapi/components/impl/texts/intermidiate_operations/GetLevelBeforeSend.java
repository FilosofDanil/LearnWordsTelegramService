package com.example.telegramapi.components.impl.texts.intermidiate_operations;

import com.example.telegramapi.components.TextHandler;
import com.example.telegramapi.components.sup.data.QueueResolver;
import com.example.telegramapi.components.sup.word_list.RandomMessageSender;
import com.example.telegramapi.entities.telegram.UserRequest;
import com.example.telegramapi.entities.telegram.UserSession;
import com.example.telegramapi.enums.States;
import com.example.telegramapi.services.ObtainTextService;
import com.example.telegramapi.services.SessionService;
import com.example.telegramapi.services.bot.TelegramBotService;
import com.example.telegramapi.threads.RandomGenerateWaitThread;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class GetLevelBeforeSend implements TextHandler {
    private final States applicable = States.WAITING_FOR_LEVEL;

    private final static Map<String, String> langMap = Map.of("A1 Elementary", "A1",
            "A2 Pre-Intermediate", "A2",
            "B1 Intermediate", "B1",
            "B2 Upper-Intermediate", "B2",
            "C1 Advanced", "C1");

    private final SessionService sessionService;

    private final ObtainTextService obtainTextService;

    private final TelegramBotService telegramService;

    private final QueueResolver resolver;

    private final RandomMessageSender randomMessageSender;

    @Override
    public void handle(UserRequest request) {
        UserSession session = sessionService.getSession(request.getChatId());
        String lang = session.getUserData().getUserSettings().getInterfaceLang();
        if(resolver.usersInQueue() > 0){
            telegramService.sendMessage(request.getChatId(), obtainTextService.read("queueShcedule", lang) + resolver.usersInQueue().toString());
        }
        RandomGenerateWaitThread randomGenerateWaitThread = new RandomGenerateWaitThread(sessionService, obtainTextService, telegramService, resolver, randomMessageSender, request);
        randomGenerateWaitThread.start();
    }

    @Override
    public States getApplicableState() {
        return applicable;
    }
}
