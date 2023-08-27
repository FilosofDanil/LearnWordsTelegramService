package com.example.telegramapi.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@PropertySource("application.yml")
public class ObtainTextService {
    @Value("${text.commands}")
    private String textPath;
    @Value("${text.ids}")
    private String textIdPath;

    public String read(String text, String lang) {
        Map<String, Map<String, String>> sections = readSectionsFromFile(textIdPath);
        String textID = cleanText(sections.get(text).get(lang));
        return cleanText(retrieveTextFromFile(textPath, textID));
    }

    private static String retrieveTextFromFile(String filename, String textId) {
        String content = "";

        try {
            content = new String(Files.readAllBytes(Paths.get(filename)));
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        Pattern textPattern = Pattern.compile(textId + ":(.*?)(?=(\\w+:|$))", Pattern.DOTALL);
        Matcher textMatcher = textPattern.matcher(content);

        if (textMatcher.find()) {
            return textMatcher.group(1).trim();
        }

        return null;
    }

    private Map<String, Map<String, String>> readSectionsFromFile(String filename) {
        Map<String, Map<String, String>> sections = new HashMap<>();
        String currentSection = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.matches("^[A-Za-z0-9_]+:$")) {
                    currentSection = line.substring(0, line.length() - 1);
                    sections.put(currentSection, new HashMap<>());
                } else if (currentSection != null) {
                    String[] parts = line.split(":");
                    if (parts.length == 2) {
                        String code = parts[0].trim();
                        String text = parts[1].trim();
                        sections.get(currentSection).put(code, text);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sections;
    }

    private String cleanText(String text) {
        Pattern pattern = Pattern.compile("\"([^\"]*)\"");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }
}
