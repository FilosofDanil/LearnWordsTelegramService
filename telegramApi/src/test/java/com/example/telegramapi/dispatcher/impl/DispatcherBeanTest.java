package com.example.telegramapi.dispatcher.impl;

import com.example.telegramapi.TelegramApiApplication;
import com.example.telegramapi.dispatcher.Dispatcher;
import lombok.RequiredArgsConstructor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = TelegramApiApplication.class)
@RunWith(SpringRunner.class)
public class DispatcherBeanTest {
    @Autowired
    private Dispatcher dispatcher;

    @Test
    public void loadContext(){
        System.out.println(dispatcher.getClass());
    }
}