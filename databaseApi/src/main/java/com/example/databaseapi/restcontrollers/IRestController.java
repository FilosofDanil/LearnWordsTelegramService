package com.example.databaseapi.restcontrollers;

import com.example.databaseapi.entities.Users;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IRestController<T> {
    ResponseEntity<List<T>> getAll();

    ResponseEntity<T> getById(Long id);

    ResponseEntity<T> create(T t);

    ResponseEntity delete(Long id);

    ResponseEntity update(T t, Long id);
}
