package com.ayhanunlu.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor

public class LogDto {
    private Long id;
    private LocalDateTime timestamp;
    private String username;
    private String actionType;
    private int amount;
    private String receiverUsername;
    private String logInfo;
}
