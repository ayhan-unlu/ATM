package com.ayhanunlu.service.impl;

import com.ayhanunlu.data.dto.UserDto;
import com.ayhanunlu.data.entity.UserEntity;
import com.ayhanunlu.mapper.UserMapper;
import com.ayhanunlu.repository.UserRepository;
import com.ayhanunlu.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServicesImpl implements UserServices {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        UserEntity userEntity = UserMapper.INSTANCE.fromUserDtoToUserEntity(userDto);
        userRepository.save(userEntity);
        return userDto;
    }

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
        UserEntity userEntity = userRepository.findByUsername(username);

        if ((userEntity != null) && (passwordEncoder.matches(password, userEntity.getPassword()))) {

            UserDto userDto = UserMapper.INSTANCE.fromUserEntityToUserDto(userEntity);
            userDto.setPassword(null);
            return userDto;
        }
        return null;
    }

    @Transactional
    @Override
    public UserEntity deposit(Integer id, int amount) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with Id: " + id));
        System.out.println("Previous Balance: " + userEntity.getBalance());
        userEntity.setBalance(userEntity.getBalance() + amount);
        System.out.println("New Balance: " + userEntity.getBalance());
        userRepository.save(userEntity);
        return userEntity;
    }

    @Transactional
    @Override
    public UserEntity withdraw(Integer id, int amount) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with Id: " + id));
        if (!checkBalance(amount, userEntity.getBalance())) {
            throw new RuntimeException("Insufficient Balance");
        } else {

            System.out.println("Previous Balance: " + userEntity.getBalance());
            userEntity.setBalance(userEntity.getBalance() - amount);
            System.out.println("New Balance: " + userEntity.getBalance());
            userRepository.save(userEntity);

            return userEntity;
        }
    }

    @Transactional
    @Override
    public UserEntity transfer(Integer senderId, Integer receiverId, int amount) {
        UserEntity senderUserEntity = userRepository.findById(senderId)
                .orElseThrow(() -> new RuntimeException("Sender not found with Id: " + senderId));
        if (!checkBalance(amount, senderUserEntity.getBalance())) {
            throw new RuntimeException("Insufficient balance");
        } else {
            UserEntity receiverUserEntity = userRepository.findById(receiverId)
                    .orElseThrow(() -> new RuntimeException("Receiver not found with Id: " + receiverId));
            senderUserEntity.setBalance(senderUserEntity.getBalance() - amount);
            userRepository.save(senderUserEntity);
            receiverUserEntity.setBalance(receiverUserEntity.getBalance() + amount);
            userRepository.save(receiverUserEntity);
            return senderUserEntity;
        }
    }

    @Override
    public boolean checkBalance(int amount, int balance) {
        return balance>=amount;
    }


}
