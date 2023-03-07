package com.app.user.model;

import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.app.user.validation.CreateValidationGroup;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@SpringBootTest
public class UserTests {
    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Test valid user") 
    public void testValidUser() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("doe");
        user.setEmail("johndoe@gmail.com");
        user.setPassword("Passw0rd#");
        Set<ConstraintViolation<User>> violation = validator.validate(user);
        Assertions.assertEquals(0, violation.size());
    }
    
    
    @Test
    @DisplayName("Test Blank First Name")
    public void testBlankFirstName() {
        User user = new User();
        user.setFirstName("");
        user.setLastName("Doe");
        user.setEmail("johndoe@gmail.com");
        user.setPassword("Passw0rd#");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        Assertions.assertEquals(1, violations.size());
    }

    @Test
    @DisplayName("Test Blank Last Name")
    public void testBlankLastName() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("");
        user.setEmail("johndoe@gmail.com");
        user.setPassword("Passw0rd#");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        Assertions.assertEquals(1, violations.size());
    }

    @Test
    @DisplayName("Test Blank Email")
    public void testBlankEmail() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("");
        user.setPassword("Passw0rd#");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        Assertions.assertEquals(1, violations.size());
    }

    @Test
    @DisplayName("Test Invalid Email")
    public void testInvalidEmail() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("johndoegmail.com");
        user.setPassword("Passw0rd#");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        Assertions.assertEquals(1, violations.size());
    }

    @Test
    @DisplayName("Test Blank Password")
    public void testBlankPassword() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("johndoe@gmail.com");
        user.setPassword("");
        Set<ConstraintViolation<User>> violations = validator.validate(user, CreateValidationGroup.class);
        Assertions.assertEquals(2, violations.size());
    }

    @Test
    @DisplayName("Test Invalid Password")
    public void testInvalidPassword() {
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("johndoe@gmail.com");
        user.setPassword("password");
        Set<ConstraintViolation<User>> violations = validator.validate(user, CreateValidationGroup.class);
        Assertions.assertEquals(1, violations.size());
    }
}
