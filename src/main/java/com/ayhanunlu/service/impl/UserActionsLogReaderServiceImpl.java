package com.ayhanunlu.service.impl;

import com.ayhanunlu.data.dto.LogDto;
import com.ayhanunlu.repository.UserRepository;
import com.ayhanunlu.service.UserActionsLogReaderService;
import com.ayhanunlu.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserActionsLogReaderServiceImpl implements UserActionsLogReaderService {

    @Autowired
    UserServices userServices;

    private static final String filePath = "/Users/ayhanunlu/IdeaProjects/ATM/log/user_actions.log";

    @Override
    public List<String> getLogLineFromFile(String filePath) {
        try {
            System.out.println("1");
            List<String> logLineList = Files.readAllLines(Paths.get(filePath));
            System.out.println(logLineList);
            return logLineList;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new ArrayList<>();
    }

    @Override
    public List<LogDto> getLogDtoFromLogLineList(List<String> logLineList) {
        List<LogDto> logDtoList = new ArrayList<>();
        for (String logLine : logLineList) {
            String[] logLineParts = logLine.split(" \\| ");

            Long id = Long.parseLong(logLineParts[0].replace("Id: ", "").trim());
            LocalDateTime timestamp = LocalDateTime.parse(logLineParts[1].trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String level = logLineParts[2].trim();
            String username = logLineParts[3].replace("User: ", "").trim();
            String actionType = logLineParts[4].replace("Action: ", "").trim();
            int amount = Integer.parseInt(logLineParts[5].replace("Amount: ", "").trim());
            String receiverUsername = logLineParts[6].replace("Receiver: ", "").trim();
            String logInfo = logLineParts[7].replace("Info: ", "").trim();

            logDtoList.add(new LogDto(id, timestamp, username, actionType, amount, receiverUsername, logInfo));
        }
        return logDtoList;
    }

    public List<LogDto> getLogDtoListByUserId(Long userId) {
        List<LogDto> logDtoListByUserId = new ArrayList<>();
        String username = userServices.getUserById(userId).getUsername();
        System.out.println("username : " + username);
        for (LogDto logDto : getLogDtoFromLogLineList(getLogLineFromFile(filePath))) {
            if (logDto.getUsername().equals(username)) {
                logDtoListByUserId.add(logDto);
            }
        }
        return logDtoListByUserId;
    }

    public List<LogDto> getLogDtoListByUserIdPaged(Long userId, int page, int pageSize){
        List<LogDto> logDtoListByUserId = getLogDtoListByUserId(userId);
        int fromIndex = page * pageSize;
        if(fromIndex>=logDtoListByUserId.size()){
            return new ArrayList<>();
        }
        if(page<0||fromIndex>=logDtoListByUserId.size()){
            return new ArrayList<>();
        }
        int toIndex =Math.min(fromIndex+pageSize,logDtoListByUserId.size());
        return logDtoListByUserId.subList(fromIndex,toIndex);
    }

    public int getTotalPages(Long userId,int pageSize){
        int totalLogs= getLogDtoListByUserId(userId).size();
        return (totalLogs+pageSize-1)/pageSize;
    }
}
