package com.ayhanunlu.config;

import com.ayhanunlu.data.dto.BeanDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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

    @Bean
    public PasswordEncoder passwordEncoder(){
        return (PasswordEncoder)new BCryptPasswordEncoder();
    }


}
