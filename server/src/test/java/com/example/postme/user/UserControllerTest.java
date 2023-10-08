package com.example.postme.user;

import com.example.postme.user.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    @InjectMocks
    private UserController userController;
    @Mock
    private UserService userService;
    private UserDto user1;

    @BeforeEach
    public void create() {
        user1 = UserDto.builder()
                .email("john@mail.ru")
                .username("john")
                .build();
    }

    @Test
    void addTest() {
        when(userService.add(user1)).thenReturn(user1);

        UserDto actualUser = userController.add(user1);

        assertEquals(user1.getUsername(), actualUser.getUsername());
        assertEquals(user1.getEmail(), actualUser.getEmail());
        verify(userService).add(user1);
    }
}