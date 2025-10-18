package com.ayhanunlu.controller;

import com.ayhanunlu.data.dto.UserDto;
import com.ayhanunlu.data.entity.UserEntity;
import com.ayhanunlu.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class BankController {

@Autowired
UserServices userServices;
    // ROOT
    // http://localhost:8080/bank_dashboard
    @GetMapping("/bank_dashboard")
    public String bank_dashboard(Model model) {
        model.addAttribute("userList",getAllUserList());
        return "bank_dashboard";
    }

    // LIST
    public List<UserDto> getAllUserList(){
        List<UserDto> allUserList = new ArrayList<>();

        allUserList=userServices.getAllUsers();
        return allUserList;
    }




}
