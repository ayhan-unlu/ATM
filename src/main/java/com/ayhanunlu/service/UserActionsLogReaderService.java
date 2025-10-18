package com.ayhanunlu.service;

import com.ayhanunlu.data.dto.LogDto;

import java.util.List;

public interface UserActionsLogReaderService {
    String filePath = "/Users/ayhanunlu/IdeaProjects/ATM/log/user_actions.log";

    List<String> getLogLineFromFile(String filePath);
    List<LogDto> getLogDtoFromLogLineList(List<String>logLineList);

}
