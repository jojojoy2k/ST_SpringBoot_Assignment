package com.app.user.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.app.user.model.User;
import com.app.user.service.UserService;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@RestClientTest
@ExtendWith(MockitoExtension.class)
public class UserControllerTests {
    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private User user;

    @BeforeEach
    void setup() {
        user = new User();
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("johndoe@gmail.com");
        user.setPassword("password#123");
    }

    @Test
    public void testCreateUser() {
        when(userService.createUser(user)).thenReturn(user);

        User createdUser = userController.createUser(user);

        assertEquals(user, createdUser);
        verify(userService).createUser(user);
    }

    @Test
    public void testCreateUsers() {
        List<User> users = new ArrayList<>();
        User user1 = user;
        users.add(user1);

        User user2 = new User();
        user2.setFirstName("Jane");
        user2.setLastName("Doe");
        user2.setEmail("jane.doe@example.com");
        user2.setPassword("Password123");
        users.add(user2);

        when(userService.createUser(users)).thenReturn(users);

        List<User> createdUsers = userController.createUser(users);

        assertEquals(users, createdUsers);
        verify(userService).createUser(users);
    }

    @Test
    public void testGetUser() {
        when(userService.getUserById(1L)).thenReturn(user);

        User foundUser = userController.getUser(1L);

        assertEquals(user, foundUser);
        verify(userService).getUserById(1L);
    }

    @Test
    public void testGetUserByEmail() {
        when(userService.getUserByEmail("john.doe@example.com")).thenReturn(user);

        User foundUser = userController.getUser("john.doe@example.com");

        assertEquals(user, foundUser);
        verify(userService).getUserByEmail("john.doe@example.com");
    }

    @Test
    public void testUpdateUser() {
        User newUser = new User();
        newUser.setFirstName("Jane");
        newUser.setLastName("Doe");
        newUser.setEmail("jane.doe@example.com");

        when(userService.updateUser(1L, newUser)).thenReturn(newUser);

        User updatedUser = userController.updateUser(1L, newUser);

        assertEquals(newUser, updatedUser);
        verify(userService).updateUser(1L, newUser);
    }

    @Test
    public void testDeleteUser() {
        Long userId = 1L;

        ResponseEntity<?> response = userController.deleteUser(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userService).deleteUserById(userId);
    }  
}
