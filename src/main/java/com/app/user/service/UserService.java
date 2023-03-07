package com.app.user.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.app.user.model.User;
import com.app.user.repository.UserRepository;

import io.micrometer.common.util.StringUtils;

@Service
public class UserService {
    private final Log logger = LogFactory.getLog(UserService.class);
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final Supplier<ResponseStatusException> responseStatusExeception = () -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, "User not found.");

    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        logger.info("Creating user: " + user.getEmail());
        return userRepository.save(user);
    }

    public List<User> createUser(List<User> users) {
        users.forEach(u -> u.setPassword(passwordEncoder.encode(u.getPassword())));
        return userRepository.saveAll(users);
    }

    public User getUserById(Long id) {
        logger.info("Getting user with ID: " + id);
        return userRepository.findById(id).orElseThrow(responseStatusExeception);
    }

    public User getUserByEmail(String email) {
        logger.info("Getting user with email: " + email);
        return userRepository.findByEmail(email);
    }

    public User updateUser(Long id, User updateUser) {
        logger.info("Updating user with ID: " + id);
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setFirstName(updateUser.getFirstName());
            user.setLastName(updateUser.getLastName());
            user.setEmail(updateUser.getEmail());
            if (StringUtils.isNotBlank(updateUser.getPassword())) {
                user.setPassword(passwordEncoder.encode(updateUser.getPassword()));
            }
            return userRepository.save(user);
        } else {
            throw responseStatusExeception.get();
        }
    }

    public void deleteUserById(Long id) {
        logger.info("Deleting user with ID: " + id);
        User user = userRepository.findById(id).orElseThrow(responseStatusExeception);
        userRepository.delete(user);
    }
}
