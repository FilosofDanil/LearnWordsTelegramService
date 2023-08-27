package com.example.databaseapi.restcontrollers.impl;

import com.example.databaseapi.entities.Users;
import com.example.databaseapi.restcontrollers.IRestController;
import com.example.databaseapi.services.DBAService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/data/users")
public class UserRestController implements IRestController<Users> {
    private final DBAService<Users> usersService;

    @Override
    @GetMapping("")
    public List<Users> getAll() {
        return usersService.getAll();
    }

    @Override
    @GetMapping("/{id}")
    public Users getById(@PathVariable("id") Long id) {
        return usersService.getById(id);
    }

    @Override
    @PostMapping("")
    public Users create(@RequestBody Users users) {
        return usersService.create(users);
    }

    @Override
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        usersService.delete(id);
    }

    @Override
    @PutMapping("/{id}")
    public void update(@RequestBody Users users, @PathVariable("id") Long id) {
        usersService.update(users, id);
    }
}
