package com.example.databaseapi.services;

import com.example.databaseapi.entities.Users;

public interface UserService {
    Users getByUsername(String username);
}
