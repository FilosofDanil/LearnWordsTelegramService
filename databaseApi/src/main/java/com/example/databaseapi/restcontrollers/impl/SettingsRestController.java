package com.example.databaseapi.restcontrollers.impl;

import com.example.databaseapi.entities.Settings;
import com.example.databaseapi.restcontrollers.IRestController;
import com.example.databaseapi.services.DBAService;
import com.example.databaseapi.services.impl.SettingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SettingsRestController implements IRestController<Settings> {
    private final DBAService<Settings> settingsService;

    @Override
    public List<Settings> getAll() {
        return settingsService.getAll();
    }

    @Override
    public Settings getById(Long id) {
        return settingsService.getById(id);
    }

    @Override
    public Settings create(Settings settings) {
        return settingsService.create(settings);
    }

    @Override
    public void delete(Long id) {
        settingsService.delete(id);
    }

    @Override
    public void update(Settings settings, Long id) {
        settingsService.update(settings, id);
    }
}
