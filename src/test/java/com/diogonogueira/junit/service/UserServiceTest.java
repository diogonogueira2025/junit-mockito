package com.diogonogueira.junit.service;

import com.diogonogueira.junit.entities.User;
import com.diogonogueira.junit.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldReturnUserWhenUserExists() {
        User user = new User(UUID.randomUUID(), "Diogo", "diogo@gmail.com");

        when(userRepository.findById(user.getId()))
                .thenReturn(Optional.of(user));

        User result = userService.getUserById(user.getId());

        assertEquals("Diogo", result.getName());
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        UUID id = UUID.randomUUID();
        when(userRepository.findById(id))
                .thenReturn(Optional.empty());

        Throwable exception = assertThrows(RuntimeException.class, () -> {
            userService.getUserById(id);
        });

        assertNotNull(exception);
    }

    @Test
    void shouldCreateUserWhenEmailDoesNotExist() {
        User user = new User(UUID.randomUUID(), "Diogo", "diogo@gmail.com");

        when(userRepository.existsByEmail(user.getEmail()))
                .thenReturn(false);

        when(userRepository.save(user))
                .thenReturn(user);

        User result = userService.createUser(user);

        assertNotNull(result);
        assertEquals("Diogo", result.getName());
        verify(userRepository).save(user);
    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        User user = new User(UUID.randomUUID(), "Diogo", "diogo@gmail.com");

        when(userRepository.existsByEmail(user.getEmail()))
                .thenReturn(true);

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> userService.createUser(user));

        assertNotNull(exception);
        verify(userRepository, never()).save(any());
    }
}
