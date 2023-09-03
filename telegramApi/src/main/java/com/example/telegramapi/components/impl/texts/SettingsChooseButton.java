package com.example.telegramapi.components.impl.texts;

import com.example.telegramapi.components.TextHandler;
import com.example.telegramapi.entities.UserRequest;
import com.example.telegramapi.entities.UserSession;
import com.example.telegramapi.entities.UserSettings;
import com.example.telegramapi.enums.States;
import com.example.telegramapi.services.ObtainTextService;
import com.example.telegramapi.services.SessionService;
import com.example.telegramapi.services.SettingsService;
import com.example.telegramapi.services.bot.TelegramBotService;
import com.example.telegramapi.utils.InlineKeyboardHelper;
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

    @Override
    public void handle(UserRequest request) {
        UserSession session = sessionService.getSession(request.getChatId());
        session = sessionService.checkUseData(session, request);
        String message = request.getUpdate().getMessage().getText();
        String lang = session.getUserData().getUserSettings().getInterfaceLang();
        if (message.equals("🇬🇧 Змінити мову інтерфейсу") || message.equals("🇬🇧 Change interface language")) {
            session.setState(States.CHANGE_LANGUAGE);
            List<String> replyList = List.of("\uD83C\uDDFA\uD83C\uDDE6 Українська", "\uD83C\uDDEC\uD83C\uDDE7 English", obtainTextService.read("Rep004", lang));
            telegramService.sendMessage(request.getChatId(),
                    obtainTextService.read("ChooseLanguage", lang), ReplyKeyboardHelper.buildMainMenu(replyList));
        } else if (message.equals("🔙 Back to the menu tab") || message.equals("🔙 Повернутися до вкладки меню")) {
            session.setState(States.MENU);
            sessionService.saveSession(request.getChatId(), session);
            List<String> replyList = List.of(obtainTextService.read("menuBut0", lang), obtainTextService.read("menuBut1", lang), obtainTextService.read("menuBut2", lang), obtainTextService.read("menuBut3", lang), obtainTextService.read("menuBut4", lang), obtainTextService.read("menuBut5", lang));
            telegramService.sendMessage(request.getChatId(), obtainTextService.read("Menu", lang), InlineKeyboardHelper.buildInlineKeyboard(replyList, false));
        } else if (message.equals("\uD83D\uDD15 Cancel notifications") || message.equals("\uD83D\uDD15 Скасувати сповіщення") || message.equals("\uD83D\uDD14 Enable notifications") || message.equals("\uD83D\uDD14 Увімкнути сповіщення")) {
            UserSettings settings = settingsService.getSettingsByUsername(session.getUserData().getUser().getUsername());
            if (settings.getNotifications()) {
                settings.setNotifications(false);
            } else {
                settings.setNotifications(true);
            }
            session.getUserData().setUserSettings(settings);
            settingsService.update(settings.getId(), settings);
            session.setState(States.SUCCESSFULLY_CHANGED_SETTINGS);
            sessionService.saveSession(request.getChatId(), session);
            telegramService.sendMessage(request.getChatId(), obtainTextService.read("ChangedLang", lang), ReplyKeyboardHelper.buildMainMenu(List.of(obtainTextService.read("Rep004", lang))));
        } else if (message.equals("\uD83D\uDE48 Choose native languages") || message.equals("\uD83D\uDE48 Вибрати рідну мову")) {
            session.setState(States.CHANGE_NATIVE);
            sessionService.saveSession(request.getChatId(), session);
            List<String> replyList = List.of("Українська", "Русский", "English", "Deutsch");
            String definedNative = session.getUserData().getUserSettings().getNativeLang();
            if (Objects.equals(definedNative, "none")) {
                if(lang.equals("en")){
                    definedNative = " not defined";
                } else{
                    definedNative = " ще не визначена";
                }
            } else if(definedNative.equals("uk")){
                definedNative = "Українська";
            }else if(definedNative.equals("ru")){
                definedNative = "Русский";
            }else if(definedNative.equals("en")){
                definedNative = "English";
            }else if(definedNative.equals("de")){
                definedNative = "Deutsch";
            }else if(definedNative.equals("fr")){
                definedNative = "Français";
            }else if(definedNative.equals("es")){
                definedNative = "Español";
            }
            telegramService.sendMessage(request.getChatId(), obtainTextService.read("choseNative", lang) + definedNative, ReplyKeyboardHelper.buildMainMenu(replyList));
        }

    }

    @Override
    public States getApplicableState() {
        return applicable;
    }
}
