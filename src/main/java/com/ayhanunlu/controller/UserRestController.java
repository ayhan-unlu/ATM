package com.ayhanunlu.controller;

import com.ayhanunlu.data.dto.UserDto;
import com.ayhanunlu.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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
    public ResponseEntity<UserDto> loginUser(@RequestParam String username, @RequestParam String password) {
        UserDto userDto = userServices.login(username, password);
        if (userDto != null) {
            if (userDto.isBlocked()) {
                return ResponseEntity.status(HttpStatus.LOCKED).build();
            } else {
                return ResponseEntity.ok(userDto);
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    // SAVE
    //http://localhost:8080/api/v1/users
    @PostMapping("/users")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        UserDto createdUserDto = userServices.createUser(userDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdUserDto);
    }

    // LIST
    // http://localhost:8080/api/v1/users
    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userServices.getAllUsers());
    }

}
