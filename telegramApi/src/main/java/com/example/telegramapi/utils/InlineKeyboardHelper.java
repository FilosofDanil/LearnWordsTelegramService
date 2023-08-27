package com.example.telegramapi.utils;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InlineKeyboardHelper {
    public static InlineKeyboardMarkup buildInlineKeyboard(List<String> rows, boolean inline) {
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();


        // Set the keyboard to the markup
        if(inline){
            List<InlineKeyboardButton> rowInline = new ArrayList<>();
            rows.forEach(row ->{
                InlineKeyboardButton button = new InlineKeyboardButton();
                button.setCallbackData(row);
                button.setText(row);
                rowInline.add(button);
            });
            rowsInline.add(rowInline);
        }else{
            rows.forEach(row ->{
                List<InlineKeyboardButton> rowInline = new ArrayList<>();
                InlineKeyboardButton button = new InlineKeyboardButton();
                button.setCallbackData(row);
                button.setText(row);
                rowInline.add(button);
                rowsInline.add(rowInline);
            });

        }

        return InlineKeyboardMarkup.builder()
                .keyboard(rowsInline)
                .build();


    }

    public static InlineKeyboardMarkup buildInlineKeyboard(List<String> rows, boolean inline, Map<String, String> map) {
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();


        // Set the keyboard to the markup
        if(inline){
            List<InlineKeyboardButton> rowInline = new ArrayList<>();
            rows.forEach(row ->{
                InlineKeyboardButton button = new InlineKeyboardButton();
                button.setCallbackData(map.get(row));
                button.setText(row);
                rowInline.add(button);
            });
            rowsInline.add(rowInline);
        }else{
            rows.forEach(row ->{
                List<InlineKeyboardButton> rowInline = new ArrayList<>();
                InlineKeyboardButton button = new InlineKeyboardButton();
                button.setCallbackData(map.get(row));
                button.setText(row);
                rowInline.add(button);
                rowsInline.add(rowInline);
            });

        }

        return InlineKeyboardMarkup.builder()
                .keyboard(rowsInline)
                .build();


    }
}