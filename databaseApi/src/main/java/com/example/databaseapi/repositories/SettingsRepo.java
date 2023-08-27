package com.example.databaseapi.repositories;

import com.example.databaseapi.entities.Settings;
import org.springframework.data.repository.CrudRepository;

public interface SettingsRepo extends CrudRepository<Long, Settings> {
}
