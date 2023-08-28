package com.example.telegramapi.client;

import com.example.telegramapi.entities.User;
import com.example.telegramapi.entities.UserSettings;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "databaseApi", url = "${application.config.database-url}")
public interface DataClient {
    @PostMapping("/users")
    User create(User user);
    @GetMapping("/users/username/{username}")
    User getByUsername(@PathVariable("username") String username);
    @GetMapping("/settings/username/{username}")
    UserSettings getSettingsByUsername(@PathVariable("username") String username);
}
