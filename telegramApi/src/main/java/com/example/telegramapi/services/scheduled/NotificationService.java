package com.example.telegramapi.services.scheduled;

import com.example.telegramapi.entities.tests_data.TestEntity;
import com.example.telegramapi.entities.user.User;
import com.example.telegramapi.entities.telegram.UserSession;
import com.example.telegramapi.enums.States;
import com.example.telegramapi.services.*;
import com.example.telegramapi.services.bot.TelegramBotService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final TelegramBotService telegramService;

    private final SessionService sessionService;

    private final ObtainTextService obtainTextService;

    private final UserService userService;

    private final TestService testService;

    private final SettingsService settingsService;

    @Scheduled(cron = "0 * * ? * *")
    public void notifyAllUsers() {
        List<User> userList = userService.getAll();
        userList.forEach(user -> {
            List<TestEntity> tests = testService.getAllByUserId(user.getId());
            UserSession session = sessionService.getSession(user.getChatId());
            LocalDateTime localDateTime = LocalDateTime.now().plus(Duration.of(1, ChronoUnit.DAYS));
            LocalDateTime plusTime = LocalDateTime.now().plus(Duration.of(30, ChronoUnit.MINUTES));
            if (settingsService.getSettingsByUsername(user.getUsername()).getNotifications()) {
                tests.forEach(testEntity -> {
                    if (!testEntity.getNotified()) {
                        if (testEntity.getTestDate().after(Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()))) {
                            sendMessage(testEntity, user.getChatId(), session.getUserData().getUserSettings().getInterfaceLang());
                        }
                    }
                    if (!testEntity.getFirstNotify() && !testEntity.getTestDate().before(new Date()) && !session.getState().equals(States.RETURNED_USER_LIST) && !session.getState().equals(States.TEST_STARTED)) {
                        if (testEntity.getTestDate().before(Date.from(plusTime.atZone(ZoneId.systemDefault()).toInstant()))) {
                            sendPreMessage(testEntity, user.getChatId(), session.getUserData().getUserSettings().getInterfaceLang());
                        }
                    }

                });
            }
        });
    }

    private void sendMessage(TestEntity testEntity, Long chatId, String lang) {
        testEntity.setNotified(true);
        testService.update(testEntity, testEntity.getId());
        telegramService.sendMessage(chatId, obtainTextService.read("notifications", lang));
    }

    private void sendPreMessage(TestEntity testEntity, Long chatId, String lang) {
        testEntity.setFirstNotify(true);
        testService.update(testEntity, testEntity.getId());
        telegramService.sendMessage(chatId, obtainTextService.read("notifyBefore", lang));
    }
}
