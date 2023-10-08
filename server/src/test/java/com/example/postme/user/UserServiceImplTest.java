package com.example.postme.user;

import com.example.postme.exception.DuplicateException;
import com.example.postme.user.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    private User user1;

    @BeforeEach
    public void create() {
        user1 = User.builder()
                .email("john@mail.ru")
                .name("john")
                .build();
    }

    @Test
    void addTest() {
        when(userRepository.save(any())).thenReturn(user1);

        UserDto actualUser = userService.add(UserMapper.toUserDto(user1));

        verify(userRepository).save(any());
        assertEquals(user1.getName(), actualUser.getUsername());
        assertEquals(user1.getEmail(), actualUser.getEmail());
    }

    @Test
    void addExceptionTest() {
        when(userRepository.save(any())).thenThrow(DuplicateException.class);

        assertThrows(DuplicateException.class, () -> userService.add(UserMapper.toUserDto(user1)));
        verify(userRepository).save(any());
    }
}