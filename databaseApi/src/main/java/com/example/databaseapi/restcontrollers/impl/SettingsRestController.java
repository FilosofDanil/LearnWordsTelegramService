package com.example.databaseapi.restcontrollers.impl;

import com.example.databaseapi.entities.Settings;
import com.example.databaseapi.restcontrollers.IRestController;
import com.example.databaseapi.services.DBAService;
import com.example.databaseapi.services.SettingsService;
import com.example.databaseapi.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<Settings>> getAll() {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(settingsService.getAll());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Settings> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(settingsService.getById(id));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<Settings> getByUsername(@PathVariable("username") String username) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(userSettingsService.getSettingsByUser(userService.getByUsername(username)));
    }

    @Override
    @PostMapping("")
    public ResponseEntity<Settings> create(@RequestBody @Valid Settings settings) {
        try {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(settingsService.create(settings));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        settingsService.delete(id);
        return ResponseEntity.ok().body(HttpStatus.OK);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody @Valid Settings settings, @PathVariable("id") Long id) {
        settingsService.update(settings, id);
        return ResponseEntity.ok().body(HttpStatus.OK);
    }
}
