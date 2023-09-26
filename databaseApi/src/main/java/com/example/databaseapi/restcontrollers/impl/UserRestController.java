package com.example.databaseapi.restcontrollers.impl;

import com.example.databaseapi.entities.Users;
import com.example.databaseapi.restcontrollers.IRestController;
import com.example.databaseapi.services.DBAService;
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
@RequestMapping("api/v1/data/users")
public class UserRestController implements IRestController<Users> {
    private final DBAService<Users> userDBAService;
    private final UserService userService;

    @Override
    @GetMapping("")
    public ResponseEntity<List<Users>> getAll() {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(userDBAService.getAll());
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Users> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(userDBAService.getById(id));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<Users> getByUsername(@PathVariable("username") String username) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(userService.getByUsername(username));
    }

    @Override
    @PostMapping("")
    public ResponseEntity<Users> create(@RequestBody @Valid Users users) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(userDBAService.create(users));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        userDBAService.delete(id);
        return ResponseEntity.ok().body(HttpStatus.OK);
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody @Valid Users users, @PathVariable("id") Long id) {
        userDBAService.update(users, id);
        return ResponseEntity.ok().body(HttpStatus.OK);
    }
}
