package com.example.databaseapi.services;

import java.util.List;

public interface DBAService<T> {
    List<T> getAll();

    T getById(Long id);

    T create(T t);

    void delete(Long id);

    void update(T t, Long id);
}
