package com.example.telegramapi.components.sup.test;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class QuizGenerateComponent {

    public Map<Character, List<Integer>> generateQuiz(List<Character> lettersList){
        Map<Character, List<Integer>> replacedMap = new HashMap<>();

        // Randomly select positions to replace
        Random random = new Random();
        int numberOfPositionsToReplace = lettersList.size() / 2;

        while (numberOfPositionsToReplace > 0) {
            int randomPosition = random.nextInt(lettersList.size());

            if (!replacedMap.containsKey(lettersList.get(randomPosition))) {
                Character letter = lettersList.get(randomPosition);
                if (letter.equals('_')) continue;
                List<Integer> indexes = new ArrayList<>();
                lettersList.forEach(letters -> {
                    if (letters.equals(letter)) {
                        int idx = lettersList.indexOf(letter);
                        indexes.add(idx);
                        lettersList.set(idx, '_');
                    }
                });
                replacedMap.put(letter, indexes);
                numberOfPositionsToReplace--;
            }
        }
        return replacedMap;
    }
}
