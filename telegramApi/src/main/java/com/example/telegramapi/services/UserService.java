package com.example.telegramapi.services;

import com.example.telegramapi.entities.User;

import java.util.List;

public interface UserService {
    List<User> getAll();

    User getByUsername(String username);

    User getById(Long id);

    User create(User user);

    void delete(Long id);

    void update(Long id, User user);
}
