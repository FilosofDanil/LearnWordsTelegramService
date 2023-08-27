package com.example.databaseapi.restcontrollers.impl;

import com.example.databaseapi.entities.Users;
import com.example.databaseapi.restcontrollers.IRestController;
import com.example.databaseapi.services.DBAService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserRestController implements IRestController<Users> {
    private final DBAService<Users> usersService;

    @Override
    public List<Users> getAll() {
        return usersService.getAll();
    }

    @Override
    public Users getById(Long id) {
        return usersService.getById(id);
    }

    @Override
    public Users create(Users users) {
        return usersService.create(users);
    }

    @Override
    public void delete(Long id) {
        usersService.delete(id);
    }

    @Override
    public void update(Users users, Long id) {
        usersService.update(users, id);
    }
}
