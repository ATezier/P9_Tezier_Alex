package com.openclassrooms.medilabo.service;

import com.openclassrooms.medilabo.model.User;
import com.openclassrooms.medilabo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    boolean isValid(User user) {
        boolean isValid = true;
        String errorMessage = "";
        if(user.getEmail() == null || user.getEmail().isEmpty()) {
            isValid = false;
            errorMessage += "Email is required. ";
        }
        if(user.getPassword() == null || user.getPassword().isEmpty()) {
            isValid = false;
            errorMessage += "Password is required. ";
        }
        if(user.getFirstName() == null || user.getFirstName().isEmpty()) {
            isValid = false;
            errorMessage += "FirstName is required. ";
        }
        if(user.getLastName() == null || user.getLastName().isEmpty()) {
            isValid = false;
            errorMessage += "LastName is required. ";
        }
        if(user.getRole() == null || user.getRole().isEmpty()) {
            isValid = false;
            errorMessage += "Role is required. ";
        }
        if(!isValid) {
            throw new IllegalArgumentException(errorMessage);
        }
        return true;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
    public User findByFirstNameAndLastName(String firstName, String lastName) {
        return userRepository.findByFirstNameAndLastName(firstName, lastName).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User create(User user) {
        if(isValid(user)) {
            try {
                findByEmail(user.getEmail());
                throw new IllegalArgumentException("User already exists, failed to create user");
            } catch (IllegalArgumentException e) {
                return userRepository.save(user);
            }
        } else {
            throw new IllegalArgumentException("User is not valid");
        }
    }

    public User update(User user) {
        if(isValid(user)) {
            try {
                findByEmail(user.getEmail());
                return userRepository.save(user);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException(e.getMessage() + ", failed to update user");
            }
        } else {
            throw new IllegalArgumentException("User is not valid");
        }
    }

    public void delete(User user) {
        try {
            findByEmail(user.getEmail());
            userRepository.delete(user);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage() + ", to delete user");
        }
    }
}
