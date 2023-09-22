package com.example.telegramapi.components.impl.texts.intermidiate_operations;

import com.example.telegramapi.components.TextHandler;
import com.example.telegramapi.components.sup.data.QueueResolver;
import com.example.telegramapi.components.sup.word_list.RandomMessageSender;
import com.example.telegramapi.entities.gpt.GPTRequest;
import com.example.telegramapi.entities.telegram.UserRequest;
import com.example.telegramapi.entities.telegram.UserSession;
import com.example.telegramapi.entities.user.UserData;
import com.example.telegramapi.enums.States;
import com.example.telegramapi.services.ObtainTextService;
import com.example.telegramapi.services.SessionService;
import com.example.telegramapi.services.bot.TelegramBotService;
import com.example.telegramapi.threads.PreparingRequestHandler;
import com.example.telegramapi.utils.ReplyKeyboardHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
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
        String message = request.getUpdate().getMessage().getText();
        String lang = session.getUserData().getUserSettings().getInterfaceLang();
        if (langMap.containsKey(message)) {
            String definedLang = langMap.get(message);
            session.setState(States.GENERATED_RANDOM_LIST);
            String randomList = getRandomList(session, definedLang);
            updateData(session, randomList);
            sessionService.saveSession(request.getChatId(), session);
            telegramService.sendMessage(request.getChatId(), randomList, ReplyKeyboardHelper.buildMainMenu(List.of(obtainTextService.read("randReturn", lang), obtainTextService.read("Rep004", lang), obtainTextService.read("tryAgain", lang))));
        } else {
            telegramService.sendMessage(request.getChatId(), obtainTextService.read("levelEx", lang), ReplyKeyboardHelper.buildMainMenu(replyList()));
            randomMessageSender.sendMessage(request);
        }

    }

    private List<String> replyList() {
        return List.of("A1 Elementary", "A2 Pre-Intermediate", "B1 Intermediate", "B2 Upper-Intermediate", "C1 Advanced");
    }

    private void updateData(UserSession session, String randomList) {
        UserData userData = session.getUserData();
        userData.setPreviousMessage(randomList);
        session.setUserData(userData);
    }

    private String getRandomList(UserSession session, String level) {
        Integer amount = session.getUserData().getInputInt();
        String lang = session.getUserData().getInputString();
        GPTRequest request = GPTRequest.builder()
                .ready(false)
                .method("random")
                .params(Map.of("amount", amount, "lang", lang, "level", level))
                .build();
        resolver.putInQueue(request);
        PreparingRequestHandler preparingRequestThread = new PreparingRequestHandler(request);
        preparingRequestThread.start();
        synchronized (preparingRequestThread) {
            try {
                preparingRequestThread.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        preparingRequestThread.stop();
        return resolver.getResponse(request);
    }

    @Override
    public States getApplicableState() {
        return applicable;
    }
}
