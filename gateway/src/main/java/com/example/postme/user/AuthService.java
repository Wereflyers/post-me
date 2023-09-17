package com.example.postme.user;

import com.example.postme.authConfig.JwtTokenUtils;
import com.example.postme.exception.UnauthorizedException;
import com.example.postme.exception.WrongRegException;
import com.example.postme.user.dto.JwtRequest;
import com.example.postme.user.dto.JwtResponse;
import com.example.postme.user.dto.RegistrationUserDto;
import com.example.postme.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserAuthService userService;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;
    private final UserClient userClient;

    public ResponseEntity<Object> createAuthToken(@RequestBody JwtRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new UnauthorizedException("Неправильный логин или пароль");
        }
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtils.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    public ResponseEntity<Object> createNewUser(@RequestBody RegistrationUserDto registrationUserDto) {
        if (!registrationUserDto.getPassword().equals(registrationUserDto.getConfirmPassword())) {
            throw new WrongRegException("Пароли не совпадают");
        }
        if (userService.findByUsername(registrationUserDto.getUsername()).isPresent()) {
            throw  new WrongRegException("Пользователь с указанным именем уже существует");
        }
        UserAuth user = userService.createNewUser(registrationUserDto);
        return userClient.addUser(new UserDto(user.getId(), user.getUsername(), registrationUserDto.getEmail()));
    }
}