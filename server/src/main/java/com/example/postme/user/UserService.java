package com.example.postme.user;

import com.example.postme.user.dto.UserDto;

import java.util.List;

public interface UserService {
    //TODO docs

    UserDto add(UserDto userDto);

    List<UserDto> getAll();

    UserDto get(long id);

    UserDto update(long id, UserDto userDto);

    void delete(long id);
}
