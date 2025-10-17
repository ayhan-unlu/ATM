package com.ayhanunlu.controller;

import com.ayhanunlu.data.dto.UserDto;
import com.ayhanunlu.data.entity.UserEntity;
import com.ayhanunlu.mapper.UserMapper;
import com.ayhanunlu.repository.UserRepository;
import com.ayhanunlu.service.BankHelper;
import com.ayhanunlu.service.UserServices;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ThymeleafController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserServices userServices;

    @Autowired
    HttpSession httpSession;
    @Autowired
    private PasswordEncoder passwordEncoder;

    //    @Autowired
    private UserMapper userMapper = UserMapper.INSTANCE;

/*    @Autowired
    private UserMapper userMapper;*/

    // DashBoard
    // http://localhost:8080/dashboard
    @GetMapping("/dashboard")
    public String getDashboard(HttpSession httpSession, Model model) {
        if (!sessionLoginCheck(httpSession)) {
            return "redirect:/login";
        }
        model.addAttribute("user", httpSession.getAttribute("loggedInUser"));
        return "dashboard";
    }

    // Deposit
    // http://localhost:8080/deposit
    @GetMapping("/deposit")
    public String getDeposit(HttpSession httpSession, Model model) {
        if (!sessionLoginCheck(httpSession)) {
            return "redirect:/login";
        }
        model.addAttribute("user", httpSession.getAttribute("loggedInUser"));
        return "deposit";
    }

    // WithDraw
    // http://localhost:8080/withdraw
    @GetMapping("/withdraw")
    public String getWithdraw(HttpSession httpSession, Model model) {
        if (!sessionLoginCheck(httpSession)) {
            return "redirect:/login";
        }
        model.addAttribute("user", httpSession.getAttribute("loggedInUser"));
        return "withdraw";
    }

    // Transfer
    // http://localhost:8080/transfer
    @GetMapping("/transfer")
    public String getTransfer(HttpSession httpSession, Model model) {
        if (!sessionLoginCheck(httpSession)) {
            return "redirect:/login";
        }
        model.addAttribute("user", httpSession.getAttribute("loggedInUser"));
        return "transfer";
    }

    // View Balance
    // http://localhost:8080/viewbalance
    @GetMapping("/viewbalance")
    public String getViewBalance(HttpSession httpSession, Model model) {
        if (!sessionLoginCheck(httpSession)) {
            return "redirect:/login";
        }
        model.addAttribute("user", httpSession.getAttribute("loggedInUser"));
        //Usermodel.getAttribute("user")
    /*    UserDto userDto = UserMapper.INSTANCE.fromUserEntityToUserDto((UserEntity) httpSession.getAttribute("loggedInUser"));
        bankHelper.viewBalance(userDto);*/
        return "viewbalance";
    }

    // Login
    // http://localhost:8080/login
    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }

    // Login
    // http://localhost:8080/login
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession httpSession, Model model) {
        Integer loginAttempts = (Integer) httpSession.getAttribute("loginAttempts");
        if (loginAttempts == null) loginAttempts = 0;

        System.out.println("username: " + username + " password: " + password);
        UserEntity userEntity = userRepository.findByUsername(username);
        //  model.addAttribute("loginAttempts", loginAttempts);
        //  System.out.println("DB userEntity password: " + userEntity.getPassword());

        if (userEntity == null) {
            model.addAttribute("errorMessage", "No such Username: " + username);
            return "login";
        }
        if (userEntity.isBlocked()) {
            model.addAttribute("errorMessage", "Your account is blocked due to multiple wrong password access.Please contact the bank");
            return "login";
        }

        if (!passwordEncoder.matches(password, userEntity.getPassword())) {
            loginAttempts++;
            httpSession.setAttribute("loginAttempts", loginAttempts);
            if (loginAttempts >= 3) {
                userEntity.setBlocked(true);
                userRepository.save(userEntity);
                model.addAttribute("errorMessage", "Too many failed login attempts. Account is blocked. Connect your bank");
            } else {
                model.addAttribute("errorMessage", "Wrong Password, try again. Attempts left " + (3 - loginAttempts));
            }
            return "login";
        }
        httpSession.setAttribute("loggedInUser", userEntity);
        httpSession.removeAttribute("loginAttempts");
        return "redirect:/dashboard";
    }


    //Get to Signup PAge
//http://localhost:8080/signup
    @GetMapping("/signup")
    public String getSignUp() {
        return "signup";
    }

    // Signup
// http://localhost:8080/signup
    @PostMapping("/signup")
    public String signup(@RequestParam String username, @RequestParam String password, Model model) {
        if (userRepository.existsByUsername(username)) {
            model.addAttribute("errorMessage", "Username already exists");
            return "signup";
        }
/*
        for(UserEntity userEntity:userRepository.findAll()){
            if(username.equals(userEntity.getUsername())){
                throw new RuntimeException("Username already exists");
            }else {
*/
        UserDto userDto = new UserDto();
        userDto.setUsername(username);
        userDto.setPassword(passwordEncoder.encode(password));
        userDto.setBalance(1000);
        userDto.setType("customer");
        userRepository.save(userMapper.fromUserDtoToUserEntity(userDto));
        return "redirect:/login";
    }


    //    Deposit
//http://localhost:8080/deposit?amount=?
    @PostMapping("/deposit")
    public String deposit(@RequestParam int amount) {
        //   System.out.println("amount" + amount);
        UserEntity sessionUserEntity = (UserEntity) httpSession.getAttribute("loggedInUser");
        UserEntity updatedUserEntity = userServices.deposit(sessionUserEntity.getId(), amount);
        httpSession.setAttribute("loggedInUser", updatedUserEntity);
        return "redirect:/deposit";
    }

    // Withdraw
// http://localhost:8080/withdraw?amount=?
    @PostMapping("/withdraw")
    public String withdraw(@RequestParam int amount, Model model) {
        UserEntity sessionUserEntity = (UserEntity) httpSession.getAttribute("loggedInUser");
        try {
            UserEntity updatedUserEntity = userServices.withdraw(sessionUserEntity.getId(), amount);
            httpSession.setAttribute("loggedInUser", updatedUserEntity);
            return "redirect:/withdraw";
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("user", sessionUserEntity);
            return "withdraw";
        }
    }

    // Transfer
// http://localhost:8080/transfer?receiver=?amount=?;
    @PostMapping("/transfer")
    public String transfer(@RequestParam int receiverId, @RequestParam int amount, Model model) {
        UserEntity sessionUserEntity = (UserEntity) httpSession.getAttribute("loggedInUser");
        try {
            UserEntity updatedUserEntity = userServices.transfer(sessionUserEntity.getId(), receiverId, amount);
            httpSession.setAttribute("loggedInUser", updatedUserEntity);
            return "redirect:/transfer";
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("user", sessionUserEntity);
            return "transfer";
        }
    }


    // Logout
// http://localhost:8080/logout
    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.invalidate();
        return "redirect:/login";
    }

    public boolean sessionLoginCheck(HttpSession httpSession) {
        if (httpSession.getAttribute("loggedInUser") == null) return false;
        else return true;
    }


    //http://localhost:8080/test
    @GetMapping("/test")
    @ResponseBody
    public String test() {
        List<UserEntity> list = userRepository.findAll();
        System.out.println("DB contains " + list);
        return list.toString();
    }
}