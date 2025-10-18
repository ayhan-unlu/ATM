package com.ayhanunlu.service.impl;

import com.ayhanunlu.data.entity.UserEntity;
import com.ayhanunlu.repository.UserRepository;
import com.ayhanunlu.service.UserActionsLoggerService;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

@Service
public class UserActionsLoggerServiceImpl implements UserActionsLoggerService {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger("com.ayhanunlu.controller");
    private static final AtomicLong logCounter = new AtomicLong(1);

    @Autowired
    UserRepository userRepository;

    @Override
    public void logUserActions(Integer id, String actionType, int amount, Integer receiverId, String logInfo) {
        try {
            UserEntity loggedInUserEntity = userRepository.findById(id).orElseThrow(() -> new RuntimeException("No user found to log"));
            UserEntity receiverUserEntity = receiverId != null ? userRepository.findById(receiverId).orElseThrow(() -> new RuntimeException("No receiver user found to log")) : null;

            String loggedInUsername = loggedInUserEntity != null ? loggedInUserEntity.getUsername() : "unknown sender";
            String receiverUsername = receiverUserEntity != null ? receiverUserEntity.getUsername() : "unknown receiver";

            long logId = logCounter.getAndIncrement();
            MDC.put("id", String.valueOf(logId));

            String message = String.format("User: %s | Action: %s | Amount: %d | Receiver: %s | Info: %s",
                    loggedInUsername, actionType, amount, receiverUsername, logInfo != null ? logInfo : "-");
            logger.info(message);
        } catch (Exception e) {
            logger.error("Failed to log user action: {}", e.getMessage());
        }

    }
}
