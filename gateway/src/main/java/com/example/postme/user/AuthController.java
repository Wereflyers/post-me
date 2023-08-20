package com.example.postme.user;

import com.example.postme.user.dto.JwtRequest;
import com.example.postme.user.dto.RegistrationUserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Slf4j
@Validated
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/authentication")
    public ResponseEntity<Object> createAuthToken(@RequestBody JwtRequest authRequest) {
        log.info("Get auth token");
        return authService.createAuthToken(authRequest);
    }

    @PostMapping("/registration")
    public ResponseEntity<Object> createNewUser(@RequestBody @Valid RegistrationUserDto registrationUserDto) {
        log.info("Registration of user");
        return authService.createNewUser(registrationUserDto);
    }
}
