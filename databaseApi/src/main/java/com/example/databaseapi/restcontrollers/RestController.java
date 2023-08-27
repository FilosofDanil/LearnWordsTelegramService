package com.example.databaseapi.restcontrollers;

import java.util.List;

public interface RestController<T> {
    List<T> getAll();

    T getById(Long id);

    T create(T t);

    void delete(Long id);

    void update(T t, Long id);
}
