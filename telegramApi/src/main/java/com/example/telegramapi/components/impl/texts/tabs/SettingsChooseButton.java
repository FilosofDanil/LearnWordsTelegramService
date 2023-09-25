package com.example.telegramapi.components.impl.texts.tabs;

import com.example.telegramapi.components.TextHandler;
import com.example.telegramapi.components.sup.tab.MenuComponent;
import com.example.telegramapi.entities.telegram.UserRequest;
import com.example.telegramapi.entities.telegram.UserSession;
import com.example.telegramapi.entities.user.UserSettings;
import com.example.telegramapi.enums.States;
import com.example.telegramapi.services.ObtainTextService;
import com.example.telegramapi.services.SessionService;
import com.example.telegramapi.services.SettingsService;
import com.example.telegramapi.services.bot.TelegramBotService;
import com.example.telegramapi.utils.ReplyKeyboardHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class SettingsChooseButton implements TextHandler {
    private final States applicable = States.SETTINGS;

    private final SessionService sessionService;

    private final TelegramBotService telegramService;

    private final ObtainTextService obtainTextService;

    private final SettingsService settingsService;

    private final MenuComponent menuComponent;

    @Override
    public void handle(UserRequest request) {
        UserSession session = sessionService.getSession(request.getChatId());
        String message = request.getUpdate().getMessage().getText();
        String lang = session.getUserData().getUserSettings().getInterfaceLang();
        if (message.equals("🇬🇧 Змінити мову інтерфейсу")
                || message.equals("🇬🇧 Change interface language")) {
            changeInterfaceLang(session, request, lang);
        } else if (message.equals("🔙 Back to the menu tab")
                || message.equals("🔙 Повернутися до вкладки меню")) {
            menuComponent.handleMenuRequest(request);
        } else if (message.equals("\uD83D\uDD15 Cancel notifications")
                || message.equals("\uD83D\uDD15 Скасувати сповіщення")
                || message.equals("\uD83D\uDD14 Enable notifications")
                || message.equals("\uD83D\uDD14 Увімкнути сповіщення")) {
            changeNotifications(session, request, lang);
        } else if (message.equals("🙈 Choose translation languages")
                || message.equals("🙈 Вибрати мову перекладу")) {
            changeNative(session, request, lang);
        }

    }

    private void changeNotifications(UserSession session, UserRequest request, String lang) {
        UserSettings settings = settingsService.getSettingsByUsername(session.getUserData().getUser().getUsername());
        settings.setNotifications(!settings.getNotifications());
        session.getUserData().setUserSettings(settings);
        settingsService.update(settings.getId(), settings);
        session.setState(States.SUCCESSFULLY_CHANGED_SETTINGS);
        sessionService.saveSession(request.getChatId(), session);
        telegramService.sendMessage(request.getChatId(), obtainTextService.read("ChangedLang", lang), ReplyKeyboardHelper.buildMainMenu(List.of(obtainTextService.read("Rep004", lang))));
    }

    private void changeNative(UserSession session, UserRequest request, String lang) {
        session.setState(States.CHANGE_NATIVE);
        sessionService.saveSession(request.getChatId(), session);
        List<String> replyList = List.of("\uD83C\uDDFA\uD83C\uDDE6 Українська", "\uD83D\uDC37 Кацапська");
        String definedNative = session.getUserData().getUserSettings().getNativeLang();
        if (Objects.equals(definedNative, "none")) {
            if (lang.equals("en")) definedNative = " not defined";
            else definedNative = " ще не визначена";
        } else if (definedNative.equals("uk")) definedNative = "Українська";
        else if (definedNative.equals("ru")) definedNative = "Русский";

        telegramService.sendMessage(request.getChatId(), obtainTextService.read("choseNative", lang) + definedNative, ReplyKeyboardHelper.buildMainMenu(replyList));
    }

    private void changeInterfaceLang(UserSession session, UserRequest request, String lang) {
        session.setState(States.CHANGE_LANGUAGE);
        List<String> replyList = List.of("\uD83C\uDDFA\uD83C\uDDE6 Українська", "\uD83C\uDDEC\uD83C\uDDE7 English", obtainTextService.read("Rep004", lang));
        telegramService.sendMessage(request.getChatId(),
                obtainTextService.read("ChooseLanguage", lang), ReplyKeyboardHelper.buildMainMenu(replyList));
    }

    @Override
    public States getApplicableState() {
        return applicable;
    }
}
