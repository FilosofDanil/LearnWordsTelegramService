package com.example.dictionaryapi.services.impl;

import com.example.dictionaryapi.components.BotComponent;
import com.example.dictionaryapi.entities.TranslatedListModel;
import com.example.dictionaryapi.services.TranslationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TranslationServiceBean implements TranslationService {
    private final BotComponent botComponent;

    @Override
    public TranslatedListModel translate(String text, String langs) {
        List<String> languages = Arrays.stream(detectLanguages(langs)).toList();
        String from = languages.get(0);
        String to = languages.get(1);
        String message = botComponent.getResponseMessage(generateMessage(text, from, to));
        Map<String, String> translatedMap = getTranslationMap(message);
        Map<String, String> definitionMap = getDefinitionMap(message);
        return TranslatedListModel.builder()
                .message(message)
                .translatedMap(translatedMap)
                .definitionMap(definitionMap)
                .build();
    }

    private Map<String, String> getDefinitionMap(String message) {
        Map<String, String> definitionMap = new HashMap<>();
        String[] sections = message.split("\n\n");

        // Parse the Definition list
        String[] definitionLines = sections[1].split("\n");
        for (int i = 1; i < definitionLines.length; i++) {
            String[] parts = definitionLines[i].split(": ");
            String germanWord = parts[0];
            String ukrainianDefinition = parts[1].trim().replaceAll(",+$", "");
            definitionMap.put(germanWord, ukrainianDefinition);
        }
        return definitionMap;
    }

    private Map<String, String> getTranslationMap(String message) {
        Map<String, String> translationMap = new HashMap<>();
        String[] sections = message.split("\n\n");

        // Parse the Translation list
        String[] translationLines = sections[0].split("\n");
        for (int i = 1; i < translationLines.length; i++) {
            String[] parts = translationLines[i].split(": ");
            String germanWord = parts[0];
            String ukrainianTranslation = parts[1].trim().replaceAll(",+$", "");
            translationMap.put(germanWord, ukrainianTranslation);
        }
        return translationMap;
    }

    private String[] detectLanguages(String langs) {
        return langs.split("/");
    }

    /*
    * Translate that word list from German to Ukrainian, and give the definitions(also on Ukrainian) for all the words, which present in the list:
 Schaden, anrichten, stet, rag, durch, sondern, konrollieren, Ausfahrt, hinauf, oben, Treppe                                                                                                                                                                                                                  Please return back just translated word list with and the definition list (both separate)
So your back must be such format(in order to convinient parse it then):
Translation list:
word1: translation,
word2: translation
......
Definition list:
word1: definittion
word2: definition
......
    * * */

    private String generateMessage(String text, String from, String to) {
        String notation = "";
        if (!to.equals("English")) {
            notation = "\nPlease pay attention, that given definitions not on on English, but on " + to + "please care about that, and give also translated definitions.\n"
                    +
                    "\n  If there are present in the list: unreadable words or random letters or given word is on other language, return as response error message. Do not translate it then!!!";
        }
        return "Translate that word list from" + from + " to " +
                to + ", and give the definitions (also on " + to + ", not on +" + from + ") for all the words, which present in the list:\n" +
                text + "\nPlease return back just translated word list with and the definition list (both separate)\n" +
                notation +
                "So your back must be such format(in order to convenient parse it then):\n" +
                "Translation list:\n" +
                "word1: translation,\n" +
                "word2: translation\n" +
                "......\n" +
                "Definition list:\n" +
                "word1: definition\n" +
                "word2: definition\n" + "For example you need to translate given word list: \"Macht unterhalten stutzen\" from German to Ukrainian, as a response you must return:\n" +
                "Translation list:\n" +
                "Macht: влада\n" +
                "unterhalten: розважати\n" +
                "stutzen: здивуватися\n" +
                "\n" +
                "Definition list:\n" +
                "Macht: переважаюча сила або контроль, особливо у політичній або громадській сфері\n" +
                "unterhalten: робити щось для розваги або задоволення; забавляти або займатися якоюсь діяльністю для розваги\n" +
                "stutzen: відчувати велику несподіванку або незнайомство; бути здивованим або здивуватися, часто у зв'язку з чимось несподіваним";
    }
}
