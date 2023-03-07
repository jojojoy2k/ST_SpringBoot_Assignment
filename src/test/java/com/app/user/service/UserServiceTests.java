package com.app.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import com.app.user.model.User;
import com.app.user.repository.UserRepository;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@RestClientTest
@ExtendWith(MockitoExtension.class)
public class UserServiceTests {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @AfterAll
    public void tearDown() {
        userRepository.deleteAll();
    }

    @Captor
    ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

    private User user;

    @BeforeEach
    void setup() {
        user = new User();
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("johndoe@gmail.com");
        user.setPassword("password");
    }

    @Test
    public void testCreateUser() {
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(userCaptor.capture())).thenReturn(user);
        
        User savedUser = userService.createUser(user);

        assertEquals(user, savedUser);
        assertEquals("encodedPassword", userCaptor.getValue().getPassword());
        verify(userRepository).save(userCaptor.capture());
    }

    @Test
    public void testCreateUserWrongPassword() {
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");
        when(userRepository.save(userCaptor.capture())).thenReturn(user);
        
        User savedUser = userService.createUser(user);

        assertEquals(user, savedUser);
        assertNotEquals("wrongPassword", userCaptor.getValue().getPassword());
        verify(userRepository).save(userCaptor.capture());
    }

    @Test
    public void testCreateUsers() {
        List<User> userList = Arrays.asList(user, new User());
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.saveAll(anyList())).thenReturn(userList);

        List<User> savedUsers = userService.createUser(userList);

        assertEquals(userList, savedUsers);
        verify(userRepository).saveAll(anyList());
    }

    @Test
    public void testGetUserById() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        User retrievedUser = userService.getUserById(user.getId());

        assertEquals(user, retrievedUser);
    }

    @Test
    public void testGetUserByIdWithInvalidId() {
        when(userRepository.findById(user.getId())).thenReturn(java.util.Optional.empty());

        assertThrows(ResponseStatusException.class, () -> userService.getUserById(user.getId()));
    }

    @Test
    public void testGetUserByEmail() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);

        User retrievedUser = userService.getUserByEmail(user.getEmail());

        assertEquals(user, retrievedUser);
    }

    
    @Test
    public void testUpdateUser() {
        User updateUser = new User();
        updateUser.setFirstName("Jane");
        updateUser.setLastName("Doe");
        updateUser.setEmail("janedoe@example.com");
        updateUser.setPassword("newpassword");
        when(userRepository.findById(user.getId())).thenReturn(java.util.Optional.of(user));
        when(passwordEncoder.encode(updateUser.getPassword())).thenReturn("encodedNewPassword");
        when(userRepository.save(userCaptor.capture())).thenReturn(user);

        User updatedUser = userService.updateUser(user.getId(), updateUser);

        assertEquals(user, updatedUser);
        assertEquals(updateUser.getFirstName(), userCaptor.getValue().getFirstName());
        assertEquals(updateUser.getLastName(), userCaptor.getValue().getLastName());
        assertEquals(updateUser.getEmail(), userCaptor.getValue().getEmail());
        assertEquals("encodedNewPassword", userCaptor.getValue().getPassword());

        verify(userRepository).findById(1L);
        verify(userRepository).save(any(User.class));
        verify(passwordEncoder).encode("newpassword");
    }

    @Test
    public void testUpdateUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        
        assertThrows(ResponseStatusException.class, () -> userService.updateUser(1L, user));
        
        verify(userRepository).findById(1L);
        verify(userRepository, never()).save(any(User.class));
        verify(passwordEncoder, never()).encode(anyString());
    }
    
    @Test
    public void testUpdateUserBlankPassword() {
        User updateUser = new User();
        updateUser.setFirstName("Jane");
        updateUser.setLastName("Doe");
        updateUser.setEmail("janedoe@example.com");
        updateUser.setPassword("");
        
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);
        
        User updatedUser = userService.updateUser(1L, updateUser);
        
        assertEquals("Jane", updatedUser.getFirstName());
        assertEquals("Doe", updatedUser.getLastName());
        assertEquals("janedoe@example.com", updatedUser.getEmail());
        assertEquals("password", updatedUser.getPassword());
        
        verify(userRepository).findById(1L);
        verify(userRepository).save(any(User.class));
        verify(passwordEncoder, never()).encode(anyString());
    }

    @Test
    public void testDeleteUser() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        userService.deleteUserById(userId);

        verify(userRepository).delete(user);
    }

    @Test
    public void testDeleteUserNotFound() {
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> userService.deleteUserById(userId));

        verify(userRepository, never()).delete(any(User.class));
    }
}
