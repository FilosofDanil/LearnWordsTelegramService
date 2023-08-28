package com.example.databaseapi.repositories;

import com.example.databaseapi.entities.Settings;
import com.example.databaseapi.entities.Users;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SettingsRepo extends CrudRepository<Settings, Long> {
    List<Settings> findAll();

    Settings getById(Long id);

    Settings findByUser(Users user);

    @Modifying
    @Transactional
    @Query("UPDATE Settings s SET s.interfaceLang = :newInterfaceLang, s.nativeLang = :newNativeLang, s.notifications = :newNotifications WHERE s.id = :settingsId")
    void updateSettings(@Param("settingsId") Long settingsId, @Param("newInterfaceLang") String newInterfaceLang, @Param("newNativeLang") String newNativeLang, @Param("newNotifications") Boolean newNotifications);
}
