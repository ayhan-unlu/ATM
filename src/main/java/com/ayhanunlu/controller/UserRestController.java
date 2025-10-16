package com.ayhanunlu.controller;

import com.ayhanunlu.data.dto.UserDto;
import com.ayhanunlu.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1")
public class UserRestController {

    @Autowired
    UserServices userServices;

    @GetMapping({"/index", "/"})
    @ResponseBody
    public String getRoot() {
        return "Welcome to Your User";
//        return "index";
    }

    /// http://localhost:8080/api/v1/users/login?username=a&password=a
    @GetMapping("/users/login")
    public UserDto loginUser(@RequestParam String username, @RequestParam String password) {
        return userServices.login(username, password);
    }








}
