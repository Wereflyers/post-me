package com.example.postme.redis;

import com.example.postme.user.AuthController;
import com.example.postme.user.AuthService;
import com.example.postme.user.dto.JwtRequest;
import com.example.postme.user.dto.RegistrationUserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {
    @InjectMocks
    private AuthController authController;
    @Mock
    private AuthService authService;
    private JwtRequest user1;
    private RegistrationUserDto registrationUserDto;

    @BeforeEach
    public void create() {
        user1 = new JwtRequest("username", "password");
        registrationUserDto = new RegistrationUserDto("username", "password", "password", "email@mail.ru");
    }

    @Test
    public void createAuthTokenTest() {
        when(authService.createAuthToken(user1)).thenReturn(ResponseEntity.accepted().body(user1));

        JwtRequest actualUser = (JwtRequest) authController.createAuthToken(user1).getBody();

        assert actualUser != null;
        assertEquals(actualUser.getUsername(), user1.getUsername());
        assertEquals(actualUser.getPassword(), user1.getPassword());
        verify(authService).createAuthToken(user1);
    }

    @Test
    public void createNewUserTest() {
        when(authService.createNewUser(registrationUserDto)).thenReturn(ResponseEntity.accepted().body(registrationUserDto));

        RegistrationUserDto actualUser = (RegistrationUserDto) authController.createNewUser(registrationUserDto).getBody();

        assert actualUser != null;
        assertEquals(actualUser.getUsername(), registrationUserDto.getUsername());
        assertEquals(actualUser.getPassword(), registrationUserDto.getPassword());
        verify(authService).createNewUser(registrationUserDto);
    }
}
