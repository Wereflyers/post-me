package com.example.postme.user;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@DataJpaTest
class UserRepositoryIntegrationTest {
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void addUsers() {
        userRepository.save(User.builder()
                .email("john@mail.ru")
                .name("john")
                .build());
        userRepository.save(User.builder()
                .name("jane")
                .email("jane@mail.ru")
                .build());
    }

    @Test
    void findAllByEmailTest() {
        Optional<User> user = userRepository.findByName("john");

        assertFalse(user.isEmpty());
        assertEquals(user.get().getEmail(), "john@mail.ru");
        assertEquals(user.get().getName(), "john");
    }

    @AfterEach
    public void clean() {
        userRepository.deleteAll();
    }
}