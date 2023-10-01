package com.example.databaseapi.restcontrollers.impl;

import com.example.databaseapi.entities.Users;
import com.example.databaseapi.repositories.UserRepo;
import com.example.databaseapi.services.DBAService;
import com.example.databaseapi.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@WebMvcTest(UserRestController.class)
class UserRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DBAService<Users> userDBAService;

    @MockBean
    private UserService userService;

    @Test
    void getAllUsers_Success() throws Exception {
        List<Users> usersList = Arrays.asList(new Users(), new Users());
        Mockito.when(userDBAService.getAll()).thenReturn(usersList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/data/users"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(usersList.size()));
    }

    @Test
    void getUserById_Success() throws Exception {
        Long userId = 1L;
        Users user = new Users();
        Mockito.when(userDBAService.getById(userId)).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/data/users/{id}", userId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(user.getId()));
    }

    @Test
    void getUserByUsername_Success() throws Exception {
        String username = "john_doe";
        Users user = new Users();
        Mockito.when(userService.getByUsername(username)).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/data/users/username/{username}", username))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(user.getUsername()));
    }

    @Test
    void createUser_Success() throws Exception {
        Users user = new Users(1L, "created", "created", new Date(), 1L);
        Mockito.when(userDBAService.create(Mockito.any(Users.class))).thenReturn(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/data/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(user.getId()));
    }

    @Test
    void deleteUser_Success() throws Exception {
        Long userId = 1L;
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/data/users/{id}", userId))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(userDBAService, Mockito.times(1)).delete(userId);
    }

    @Test
    void updateUser_Success() throws Exception {
        Long userId = 1L;
        Users user = new Users(1L, "updated", "updated", new Date(), 1L);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/data/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}