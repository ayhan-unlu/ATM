package com.ayhanunlu.config;

import com.ayhanunlu.data.dto.BeanDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class BeanConfig {

    @Bean(initMethod="initialBeanMethod",destroyMethod="destroyBeanMethod")
    @Scope("singleton")
//    @Scope("request") //request
//    @Scope("session") //session
    public BeanDto beanDto() {
        return BeanDto
                .builder()
                .id(0L).beanName(" beanName   -   ATM ").beanData(" beanData   -   ATM ")
                .build();
    }
}
