package com.ayhanunlu.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor


public class UserDto {
    private Long id;
    private String username;
    private String password;
    private double balance;
    private String type;
}
