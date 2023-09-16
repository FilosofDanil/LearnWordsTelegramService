package com.example.telegramapi.components.impl.texts.backbuttons;

import com.example.telegramapi.components.TextHandler;
import com.example.telegramapi.components.sup.word_list.WordListComponentAdvicor;
import com.example.telegramapi.entities.UserRequest;
import com.example.telegramapi.enums.States;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TryAgainBackButton implements TextHandler {
    private final States applicable = States.PREPARES_LIST;

    private final WordListComponentAdvicor advicor;

    @Override
    public void handle(UserRequest request) {
        if(request.getUpdate().getMessage().getText().equals("\uD83D\uDD04 Try again") || request.getUpdate().getMessage().getText().equals("🔄 Надіслати ще раз")){
            advicor.createWordList(request);
        }
    }

    @Override
    public States getApplicableState() {
        return applicable;
    }
}
