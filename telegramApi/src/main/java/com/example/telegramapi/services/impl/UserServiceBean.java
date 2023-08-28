package com.example.telegramapi.services.impl;

import com.example.telegramapi.client.DataClient;
import com.example.telegramapi.entities.User;
import com.example.telegramapi.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceBean implements UserService {
    private final DataClient dataClient;

    @Override
    public List<User> getAll() {
        return dataClient.getAllUsers();
    }

    @Override
    public User getByUsername(String username) {
        return dataClient.getByUsername(username);
    }

    @Override
    public User getById(Long id) {
        return dataClient.getUserById(id);
    }

    @Override
    public User create(User user) {
        return dataClient.create(user);
    }

    @Override
    public void delete(Long id) {
        dataClient.deleteUser(id);
    }

    @Override
    public void update(Long id, User user) {
        dataClient.updateUser(id, user);
    }
}
