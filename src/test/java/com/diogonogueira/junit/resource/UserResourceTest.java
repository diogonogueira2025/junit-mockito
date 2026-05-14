package com.diogonogueira.junit.resource;

import com.diogonogueira.junit.entities.User;
import com.diogonogueira.junit.service.UserService;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.http.MediaType;

@WebMvcTest(UserResource.class)
public class UserResourceTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Test
    void shouldReturnUserById() throws Exception {
        User user = new User("diogo", "diogo@gmail.com");

        when(userService.getUserById(user.getId()))
                .thenReturn(user);

        mockMvc.perform(get("/api/users/" + user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("diogo"))
                .andExpect(jsonPath("$.email").value("diogo@gmail.com"));
    }

    @Test
    void shouldCreateUser() throws Exception {
        User savedUser = new User("diogo", "diogo@gmail.com");

        when(userService.createUser(any(User.class)))
                .thenReturn(savedUser);

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "name": "diogo",
                            "email": "diogo@gmail.com"
                        }
                        """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("diogo"))
                .andExpect(jsonPath("$.email").value("diogo@gmail.com"));
    }

    @Test
    void shouldReturnBadRequestForInvalidInput() throws Exception {
        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "email": "invalid"
                        }
                        """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldCallServiceWhenCreatingUser() throws Exception {
        User user = new User("diogo", "diogo@gmail.com");

        when(userService.createUser(any(User.class)))
                .thenReturn(user);

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "name": "diogo",
                            "email": "diogo@gmail.com"
                        }
                        """))
                .andExpect(status().isCreated());

        verify(userService).createUser(any(User.class));
    }
}
