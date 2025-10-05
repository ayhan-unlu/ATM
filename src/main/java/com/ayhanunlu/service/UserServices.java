package com.ayhanunlu.service;

import com.ayhanunlu.data.dto.UserDto;

import java.util.List;

public interface UserServices {

    UserDto createUser(UserDto userDto);
    List<UserDto> getAllUsers();

    UserDto login(String username, String password);

}
