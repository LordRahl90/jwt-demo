package com.lordrahl.jwtdemo.user;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserRepositoryTest {

    @Mock
    private UserRepository userRepository;

    private static User user;

    @BeforeAll
    static void setup() {
        System.out.println("This is initiated before setup");
        user = User.builder()
                .firstname("Alugbin")
                .email("tolaabbey009@gmail.com")
                .lastname("Abiodun")
                .password("password").
                build();
    }

    @Test
    void testFindByEmail() {
        when(userRepository.findByEmail(any(String.class))).thenReturn(Optional.of(user));

        Optional<User> optionalUser = userRepository.findByEmail("email.com");
        assertTrue(optionalUser.isPresent());
        assertNotNull(optionalUser.get());

        User responseUser = optionalUser.get();
        assertEquals(user.getEmail(), responseUser.getEmail());
    }
}