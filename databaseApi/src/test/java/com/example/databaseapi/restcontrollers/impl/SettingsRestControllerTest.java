package com.example.databaseapi.restcontrollers.impl;

import com.example.databaseapi.entities.Settings;
import com.example.databaseapi.entities.Users;
import com.example.databaseapi.services.DBAService;
import com.example.databaseapi.services.SettingsService;
import com.example.databaseapi.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class SettingsRestControllerTest {

    @InjectMocks
    private SettingsRestController settingsRestController;

    @Mock
    private DBAService<Settings> settingsService;

    @Mock
    private UserService userService;

    @Mock
    private SettingsService userSettingsService;

    @Test
    void getAllSettings_Success() {
        List<Settings> settingsList = Arrays.asList(new Settings(), new Settings());
        Mockito.when(settingsService.getAll()).thenReturn(settingsList);

        ResponseEntity<List<Settings>> response = settingsRestController.getAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(settingsList, response.getBody());
    }

    @Test
    void getSettingsById_Success() {
        Long id = 1L;
        Settings settings = new Settings();
        Mockito.when(settingsService.getById(id)).thenReturn(settings);

        ResponseEntity<Settings> response = settingsRestController.getById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(settings, response.getBody());
    }

    @Test
    void getSettingsByUsername_Success() {
        String username = "john_doe";
        Users user = new Users();
        Settings settings = new Settings();
        Mockito.when(userService.getByUsername(username)).thenReturn(user);
        Mockito.when(userSettingsService.getSettingsByUser(user)).thenReturn(settings);

        ResponseEntity<Settings> response = settingsRestController.getByUsername(username);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(settings, response.getBody());
    }

    @Test
    void createSettings_Success() {
        Settings settings = new Settings();
        Mockito.when(settingsService.create(Mockito.any(Settings.class))).thenReturn(settings);

        ResponseEntity<Settings> response = settingsRestController.create(settings);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(settings, response.getBody());
    }

    @Test
    void deleteSettings_Success() {
        Long id = 1L;
        ResponseEntity response = settingsRestController.delete(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Mockito.verify(settingsService, Mockito.times(1)).delete(id);
    }

    @Test
    void updateSettings_Success() {
        Long id = 1L;
        Settings settings = new Settings();
        ResponseEntity response = settingsRestController.update(settings, id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Mockito.verify(settingsService, Mockito.times(1)).update(settings, id);
    }
}