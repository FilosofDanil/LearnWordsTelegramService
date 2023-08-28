package com.example.databaseapi.restcontrollers.impl;

import com.example.databaseapi.entities.Settings;
import com.example.databaseapi.restcontrollers.IRestController;
import com.example.databaseapi.services.DBAService;
import com.example.databaseapi.services.SettingsService;
import com.example.databaseapi.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/data/settings")
public class SettingsRestController implements IRestController<Settings> {
    private final DBAService<Settings> settingsService;
    private final UserService userService;
    private final SettingsService userSettingsService;

    @Override
    @GetMapping("")
    public List<Settings> getAll() {
        return settingsService.getAll();
    }

    @Override
    @GetMapping("/{id}")
    public Settings getById(@PathVariable("id") Long id) {
        return settingsService.getById(id);
    }

    @GetMapping("/username/{username}")
    public Settings getByUsername(@PathVariable("username") String username) {
        return userSettingsService.getSettingsByUser(userService.getByUsername(username));
    }

    @Override
    @PostMapping("")
    public Settings create(@RequestBody Settings settings) {
        return settingsService.create(settings);
    }

    @Override
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        settingsService.delete(id);
    }

    @Override
    @PutMapping("/{id}")
    public void update(@RequestBody Settings settings, @PathVariable("id") Long id) {
        settingsService.update(settings, id);
    }
}
