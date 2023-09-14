package com.example.telegramapi.components.impl.texts;

import com.example.telegramapi.components.TextHandler;
import com.example.telegramapi.components.additions.QuizGenerateComponent;
import com.example.telegramapi.entities.*;
import com.example.telegramapi.enums.States;
import com.example.telegramapi.services.ObtainTextService;
import com.example.telegramapi.services.SessionService;
import com.example.telegramapi.services.TestService;
import com.example.telegramapi.services.bot.TelegramBotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class QuizTextHandler implements TextHandler {
    private final States applicable = States.QUIZ;

    private final SessionService sessionService;

    private final TelegramBotService telegramService;

    private final ObtainTextService obtainTextService;

    private final TestService testService;

    private final QuizGenerateComponent quizGenerateComponent;

    @Override
    public void handle(UserRequest request) {
        UserSession session = sessionService.getSession(request.getChatId());
        TestEntity test = session.getUserData().getCurrentTest();
        String lang = session.getUserData().getUserSettings().getInterfaceLang();
        String message = request.getUpdate().getMessage().getText();
        if (message.length() > 1) {
            telegramService.sendMessage(request.getChatId(), "Try one more!");
            return;
        }
        Character answer = getAnswer(message);
        List<Character> lettersList = generateLetterList(message);
        if (session.getUserData().getReplacedMap() == null) {
            saveMapInUserData(session, quizGenerateComponent.generateQuiz(lettersList));
        }
        Map<Character, List<Integer>> replacedMap = session.getUserData().getReplacedMap();
        if (!replacedMap.isEmpty() && session.getUserData().getQuizAttempts() > 0) {
            String response = guess(answer, lettersList, replacedMap);
            if(response.equals("Incorrect")){
                attempt(session);
            }
            sessionService.saveSession(request.getChatId(), session);
            telegramService.sendMessage(request.getChatId(), lettersToString(lettersList) + response);
        } else {
            saveMapInUserData(session, null);
            telegramService.sendMessage(request.getChatId(), obtainTextService.read("quizFinished", lang));
            startTest(session, test);
            sessionService.saveSession(request.getChatId(), session);
            telegramService.sendMessage(request.getChatId(), formTaskString(session));
        }
    }

    private void startTest(UserSession session, TestEntity test) {
        UserData userData = session.getUserData();
        userData.setCurrentTest(test);
        userData.setQuizAttempts(3);
        userData.setCurrentTask(userData.getCurrentTask() + 1);
        session.setUserData(userData);
        session.setState(States.TEST_STARTED);
    }

    private String formTaskString(UserSession session) {
        Integer currentTask = session.getUserData().getCurrentTask();
        Test task = session.getUserData().getCurrentTest().getTests().get(currentTask);
        String responseMessage = "Test Started! \n " + "Task#" + currentTask + " \n";
        return responseMessage + task.getResponseMessage();
    }

    private Character getAnswer(String message) {
        return message.charAt(0);
    }

    private List<Character> generateLetterList(String message) {
        List<Character> lowercaseLetters = new ArrayList<>();
        for (char c : message.toCharArray()) {
            lowercaseLetters.add(Character.toLowerCase(c));
        }
        return lowercaseLetters;
    }

    private String guess(Character guess, List<Character> lettersList,
                         Map<Character, List<Integer>> replacedLettersList) {
        if (replacedLettersList.containsKey(guess)) {
            List<Integer> list = replacedLettersList.get(guess);
            list.forEach(lettersIdx -> {
                lettersList.set(lettersIdx, guess);
            });
            replacedLettersList.remove(guess);
            return "Correct";
        }
        return "Incorrect";
    }

    private void saveMapInUserData(UserSession session, Map<Character, List<Integer>> replacedMap) {
        UserData userData = session.getUserData();
        userData.setQuizAttempts(3);
        userData.setReplacedMap(replacedMap);
        session.setUserData(userData);
    }

    private void attempt(UserSession session) {
        UserData userData = session.getUserData();
        userData.setQuizAttempts(userData.getQuizAttempts() - 1);
    }

    private String lettersToString(List<Character> letters) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Word: \n");
        letters.forEach(letter -> {
            stringBuilder.append(letter);
            stringBuilder.append(" ");
        });
        return stringBuilder.toString() +"\n";
    }

    @Override
    public States getApplicableState() {
        return applicable;
    }
}
