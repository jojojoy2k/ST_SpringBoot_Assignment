package com.app.user.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.user.model.User;
import com.app.user.service.UserService;
import com.app.user.validation.CreateValidationGroup;

import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping("/users")
public class UserController {
    private final Log logger = LogFactory.getLog(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping
    public User createUser(
        @RequestBody 
        @Validated(CreateValidationGroup.class) 
        User user
    ) {
        logger.debug("Creating user: " + user);
        User createdUser = userService.createUser(user);
        logger.info("User created: " + createdUser);
        return createdUser;
    }
        
    @Profile("dev")
    @PostMapping("/init")
    public List<User> createUser(@Valid @RequestBody List<User> users) {
        logger.debug("Creating userS: " + users);
        List<User> createdUsers = userService.createUser(users);
        logger.info("UserS created: " + createdUsers);
        return createdUsers;
    }
    
    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        logger.debug("Retrieving user by ID: " + id);
        User user = userService.getUserById(id);
        logger.info("User retrieved by ID: " + user);
        return user;
    }

    @GetMapping("/email")
    public User getUser(@RequestBody String email) {
        logger.debug("Retrieving user by email: " + email);
        User user = userService.getUserByEmail(email);
        logger.info("User retrieved by email: " + user);
        return user;
    }

    @PutMapping("/{id}") 
    public User updateUser(@PathVariable Long id, @Valid @RequestBody User updateUser){
        logger.debug("Updating user with ID " + id + " : " + updateUser);
        User updatedUser = userService.updateUser(id, updateUser);
        logger.info("User updated: " + updatedUser);
        return updatedUser;
    }
    
    @DeleteMapping("/{id}") 
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        logger.debug("Deleting user with ID: " + id);
        userService.deleteUserById(id);
        logger.info("User deleted with ID:" + id);
        return ResponseEntity.ok().build();
    }
}
