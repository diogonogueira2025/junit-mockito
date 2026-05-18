package com.diogonogueira.junit.resource;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserResourceIntegrationTest {

    @LocalServerPort
    private int port;

    @Test
    void shouldStartServer() {
        Assertions.assertTrue(port > 0);
    }
}
