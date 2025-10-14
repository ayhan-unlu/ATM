package com.ayhanunlu.service;

import com.ayhanunlu.data.dto.UserDto;

public interface BankServices {
    boolean deposit(UserDto userDto, double amount);
    boolean withdraw(UserDto userDto,double amount);
    boolean transfer(UserDto senderUserDto, UserDto recieverUserDto, double amount);
    double getBalance(UserDto userDto);
}
