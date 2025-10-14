package com.ayhanunlu.controller;

import com.ayhanunlu.config.BeanConfig;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.logging.Logger;

@Controller
public class BeanController {

    @Autowired
    BeanConfig beanConfig;

    //http://localhost:8080/bean/beanDto
    @GetMapping("/bean/beanDto")//url
    @ResponseBody// without html displays on screen
    public String getBeanDto() {
        return beanConfig.beanDto() + ".";
    }



}
