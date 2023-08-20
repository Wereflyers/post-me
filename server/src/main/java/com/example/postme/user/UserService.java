package com.example.postme.user;

import com.example.postme.user.dto.UserDto;

public interface UserService {
    /**
     * Добавление зарегистрированного пользователя в базу
     * @param userDto - данные пользователя
     * @return UserDto
     */
    UserDto add(UserDto userDto);
}
