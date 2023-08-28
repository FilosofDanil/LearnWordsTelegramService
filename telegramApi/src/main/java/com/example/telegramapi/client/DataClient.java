package com.example.telegramapi.client;

import com.example.telegramapi.entities.User;
import com.example.telegramapi.entities.UserSettings;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "databaseApi", url = "${application.config.database-url}")
public interface DataClient {
    @GetMapping("/users")
    List<User> getAllUsers();
    @GetMapping("/users/{id}")
    User getUserById(@PathVariable("id") Long id);
    @PostMapping("/users")
    User create(@RequestBody User user);
    @GetMapping("/users/username/{username}")
    User getByUsername(@PathVariable("username") String username);
    @GetMapping("/settings/username/{username}")
    UserSettings getSettingsByUsername(@PathVariable("username") String username);
    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable("id") Long id);
    @PutMapping("/users/{id}")
    void updateUser(@PathVariable("id") Long id, @RequestBody User user);
}
