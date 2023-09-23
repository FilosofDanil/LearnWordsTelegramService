package com.example.telegramapi.threads;

import com.example.telegramapi.components.sup.data.QueueResolver;
import com.example.telegramapi.components.sup.tab.MenuComponent;
import com.example.telegramapi.entities.gpt.GPTRequest;
import com.example.telegramapi.entities.telegram.UserRequest;
import com.example.telegramapi.entities.telegram.UserSession;
import com.example.telegramapi.enums.States;
import com.example.telegramapi.services.ObtainTextService;
import com.example.telegramapi.services.SessionService;
import com.example.telegramapi.services.bot.TelegramBotService;
import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public class TranslationWaitThread extends Thread{
    private final SessionService sessionService;

    private final ObtainTextService obtainTextService;

    private final TelegramBotService telegramService;

    private final MenuComponent menuComponent;

    private final QueueResolver resolver;

    private UserRequest request;
    @Override
    public void run() {
        String message = request.getUpdate().getMessage().getText();
        if (message.equals("🔙 Back") || request.getUpdate().getMessage().getText().equals("🔙 Назад")) {
            menuComponent.handleMenuRequest(request);
            return;
        }
        UserSession session = sessionService.getSession(request.getChatId());
        String langFrom = session.getUserData().getInputString();
        String langTo = session.getUserData().getUserSettings().getNativeLang();
        String lang = session.getUserData().getUserSettings().getInterfaceLang();
        telegramService.sendMessage(request.getChatId(), obtainTextService.read("waitMoment", lang));

        GPTRequest gptRequest = GPTRequest.builder()
                .ready(false)
                .method("translate")
                .params(Map.of("message", message, "langFrom", langFrom, "langTo", langTo))
                .build();
        resolver.putInQueue(gptRequest);
        PreparingRequestHandler preparingRequestThread = new PreparingRequestHandler(gptRequest);
        preparingRequestThread.start();
        synchronized (preparingRequestThread) {
            try {
                preparingRequestThread.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        preparingRequestThread.stop();
        String response = resolver.getResponse(gptRequest);
        session.setState(States.SUCCESSFULLY_TRANSLATED);
        sessionService.saveSession(request.getChatId(), session);
        telegramService.sendMessage(request.getChatId(), response);
    }
}
