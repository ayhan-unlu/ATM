package com.ayhanunlu.service.impl;

import com.ayhanunlu.data.dto.UserDto;
import com.ayhanunlu.data.entity.UserEntity;
import com.ayhanunlu.mapper.UserMapper;
import com.ayhanunlu.repository.UserRepository;
import com.ayhanunlu.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServicesImpl implements UserServices {

    @Autowired
    private UserRepository userRepository;

    //  SAVE
    //  http://localhost:8080/api/v1/users
    @PostMapping("/users")
    @Override
    public UserDto createUser(@RequestBody UserDto userDto) {
        UserEntity userEntity = UserMapper.INSTANCE.fromUserDtoToUserEntity(userDto);
        userRepository.save(userEntity);
        return userDto;
    }

    // LIST
    // http://localhost:8080/api/v1/users
    @GetMapping("/users")
    @Override
    public List<UserDto> getAllUsers() {
        List<UserDto> listUserDto = new ArrayList<>();
        Iterable<UserEntity> userEntityList = userRepository.findAll();
        for (UserEntity userEntity : userEntityList) {
            UserDto userDto = UserMapper.INSTANCE.fromUserEntityToUserDto(userEntity);
            listUserDto.add(userDto);
        }
        return listUserDto;
    }


    @Override
    public UserDto login(String username, String password) {
        UserEntity userEntity = userRepository.findByUsernameAndPassword(username, password);
        if (userEntity != null) {
            System.out.println("Login successfull");
            return UserMapper.INSTANCE.fromUserEntityToUserDto(userEntity);
        }
        return null;
    }


}
