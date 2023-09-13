package com.example.databaseapi.repositories;

import com.example.databaseapi.entities.Users;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface UserRepo extends CrudRepository<Users, Long> {
    List<Users> findAll();

    Users findByUsername(String username);

    @Modifying
    @Transactional
    @Query("UPDATE Users u SET u.username = :newUsername, u.registrationDate = :newRegistrationDate WHERE u.id = :userId")
    void updateUser(@Param("userId") Long userId, @Param("newUsername") String newUsername, @Param("newRegistrationDate") Date newRegistrationDate);
}
