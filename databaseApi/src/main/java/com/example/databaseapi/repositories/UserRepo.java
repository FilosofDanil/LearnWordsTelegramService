package com.example.databaseapi.repositories;

import com.example.databaseapi.entities.Users;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<Long, Users> {
}
