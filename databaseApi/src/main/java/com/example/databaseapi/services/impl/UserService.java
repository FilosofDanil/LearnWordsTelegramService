package com.example.databaseapi.services.impl;

import com.example.databaseapi.entities.Users;
import com.example.databaseapi.repositories.UserRepo;
import com.example.databaseapi.services.DBAService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements DBAService<Users> {
    private final UserRepo userRepo;

    @Override
    public List<Users> getAll() {
        return userRepo.findAll();
    }

    @Override
    public Users getById(Long id) {
        return userRepo.getById(id);
    }

    @Override
    public Users create(Users users) {
        userRepo.save(users);
        return users;
    }

    @Override
    public void delete(Long id) {
        userRepo.deleteById(id);
    }

    @Override
    public void update(Users users, Long id) {
        userRepo.updateUser(id, users.getUsername(), users.getRegistrationDate());
    }
}
