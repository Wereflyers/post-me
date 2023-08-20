package com.example.postme.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegistrationUserDto {
    @NotEmpty
    @NotBlank
    private String username;
    private String password;
    private String confirmPassword;
    @NotEmpty
    @NotBlank
    @Email
    private String email;
}
