package com.example.databaseapi.restcontrollers.impl;

import com.example.databaseapi.entities.Users;
import com.example.databaseapi.restcontrollers.IRestController;
import com.example.databaseapi.services.DBAService;
import com.example.databaseapi.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/data/users")
public class UserRestController implements IRestController<Users> {
    private final DBAService<Users> userDBAService;
    private final UserService userService;

    @Override
    @GetMapping("")
    public List<Users> getAll() {
        return userDBAService.getAll();
    }

    @Override
    @GetMapping("/{id}")
    public Users getById(@PathVariable("id") Long id) {
        return userDBAService.getById(id);
    }

    @GetMapping("/username/{username}")
    public Users getByUsername(@PathVariable("username") String username) {
        return userService.getByUsername(username);
    }

    @Override
    @PostMapping("")
    public Users create(@RequestBody Users users) {
        return userDBAService.create(users);
    }

    @Override
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        userDBAService.delete(id);
    }

    @Override
    @PutMapping("/{id}")
    public void update(@RequestBody Users users, @PathVariable("id") Long id) {
        userDBAService.update(users, id);
    }
}
