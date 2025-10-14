package com.ayhanunlu.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor


@Component
public class SwaggerBean {

    private String title = "ATM Management API";
    private String description = "API documentation for ATM project";
    private String version = "v1.0";
}
