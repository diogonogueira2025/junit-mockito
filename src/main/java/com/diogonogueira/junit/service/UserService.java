package com.diogonogueira.junit.service;

import com.diogonogueira.junit.entities.User;
import com.diogonogueira.junit.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        return userRepository.save(user);
    }


    public User getUserById(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User updateUser(UUID id, User user) {
        User user2 = getUserById(id);

        user2.setEmail(user.getEmail());
        user2.setName(user.getName());

        return userRepository.save(user2);
    }

    public void deleteUser(UUID id) {
        User user = getUserById(id);

        userRepository.delete(user);
    }
}
