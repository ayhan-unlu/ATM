package com.ayhanunlu;

import com.ayhanunlu.data.entity.UserEntity;
import com.ayhanunlu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

//@SpringBootApplication

@SpringBootApplication(exclude ={
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
        org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration.class
})


public class AtmApplication {
    @Autowired
    UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(AtmApplication.class, args);
    }


}
