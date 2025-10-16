package com.ayhanunlu.service;

import com.ayhanunlu.data.dto.UserDto;
import com.ayhanunlu.data.entity.UserEntity;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public interface UserServices {

    UserDto createUser(UserDto userDto);
    List<UserDto> getAllUsers();

    UserDto login(String username, String password);

    UserEntity deposit(Integer id, int amount);

}
