package com.ayhanunlu.service.impl;

import com.ayhanunlu.data.dto.UserDto;
import com.ayhanunlu.repository.UserRepository;
import com.ayhanunlu.service.BankServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankServicesImpl implements BankServices {

    @Autowired
    private UserRepository userRepository;

    double amount;


    @Override
    public boolean deposit(UserDto userDto, double amount){
        return true;
    }

    @Override
    public boolean withdraw(UserDto userDto, double amount){
        return true;
    }

    @Override
    public boolean transfer(UserDto senderUserDto, UserDto recieverUserDto, double amount){
        return true;
    }

    @Override
    public double getBalance(UserDto userDto){
        return 0;
    }
}
