package com.ayhanunlu.service;

import com.ayhanunlu.data.dto.UserDto;

public interface UserActionsLoggerService {
    void logUserActions(Integer id, String actionType, int amount, Integer receiverId, String logInfo);
}
