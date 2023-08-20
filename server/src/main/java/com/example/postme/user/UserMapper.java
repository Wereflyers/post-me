package com.example.postme.user;

import com.example.postme.user.dto.UserDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class UserMapper {
    public static UserDto toUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getName())
                .email(user.getEmail())
                .build();
    }

    public static User toUser(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .name(userDto.getUsername())
                .email(userDto.getEmail())
                .build();
    }
}
