package com.example.telegramapi.components.sup.word_list;

import com.example.telegramapi.components.sup.data.QueueResolver;
import com.example.telegramapi.entities.telegram.UserRequest;
import com.example.telegramapi.entities.telegram.UserSession;
import com.example.telegramapi.services.ObtainTextService;
import com.example.telegramapi.services.SessionService;
import com.example.telegramapi.services.bot.TelegramBotService;
import com.example.telegramapi.threads.WordListWaitThread;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReturnListComponent {
    private final SessionService sessionService;

    private final TelegramBotService telegramService;

    private final ObtainTextService obtainTextService;

    private final UserListCreatorComponent creator;

    private final QueueResolver resolver;

    public void sendTest(UserRequest request) {
        WordListWaitThread wordListWaitThread = new WordListWaitThread(sessionService, telegramService, obtainTextService, creator, request);
        wordListWaitThread.start();
    }
}
