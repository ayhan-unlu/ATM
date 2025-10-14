package com.ayhanunlu.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Autowired
    private SwaggerBean swaggerBean;

    @Bean
    public OpenAPI atmOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title(swaggerBean.getTitle())
                        .description(swaggerBean.getDescription())
                        .version(swaggerBean.getVersion()));
    }
}
