package com.ayhanunlu.controller;

import com.ayhanunlu.data.dto.LogDto;
import com.ayhanunlu.data.dto.UserDto;
import com.ayhanunlu.data.entity.UserEntity;
import com.ayhanunlu.mapper.UserMapper;
import com.ayhanunlu.repository.UserRepository;
import com.ayhanunlu.service.UserActionsLoggerService;
import com.ayhanunlu.service.UserServices;
import com.ayhanunlu.service.impl.UserActionsLogReaderServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ThymeleafController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserServices userServices;

    @Autowired
    UserActionsLoggerService userActionsLoggerService;

    @Autowired
    HttpSession httpSession;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private BankController bankController;

    //    @Autowired
    private UserMapper userMapper = UserMapper.INSTANCE;

    private static final Logger logger = LoggerFactory.getLogger(ThymeleafController.class);
    @Autowired
    private UserActionsLogReaderServiceImpl userActionsLogReaderService;

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
            userActionsLoggerService.logUserActions(userEntity.getId(), "BLOCKED LOGIN ATTEMPT", 0, null, "FAIL");
            return "login";
        }

        if (!passwordEncoder.matches(password, userEntity.getPassword())) {
            loginAttempts++;
            httpSession.setAttribute("loginAttempts", loginAttempts);
            if (loginAttempts >= 3) {
                userEntity.setBlocked(true);
                userRepository.save(userEntity);
                model.addAttribute("errorMessage", "Too many failed login attempts. Account is blocked. Connect your bank");
                userActionsLoggerService.logUserActions(userEntity.getId(), "USER BLOCKED", 0, null, "FAIL");

            } else {
                model.addAttribute("errorMessage", "Wrong Password, try again. Attempts left " + (3 - loginAttempts));
                userActionsLoggerService.logUserActions(userEntity.getId(), "WRONG PASSWORD", 0, null, "FAIL");
            }
            return "login";
        }
        httpSession.setAttribute("loggedInUser", userEntity);
        httpSession.removeAttribute("loginAttempts");
        if (userEntity.getType().equals("bank")) {

            userActionsLoggerService.logUserActions(userEntity.getId(), "BANK LOGIN", 0, null, "Successful");
            return "redirect:/bank_dashboard";
        } else {
            userActionsLoggerService.logUserActions(userEntity.getId(), "USER LOGIN", 0, null, "Successful");
            return "redirect:/dashboard";
        }
    }


    // User Actions Logs
    // http://localhost:8080/user_action/{id}
    @GetMapping("/user_actions/{id}")
    public String getUserActions(
            @PathVariable Long id,
            @RequestParam (defaultValue="0") int page,
            @RequestParam(defaultValue="10") int pageSize,
            HttpSession httpSession,
            Model model){
        List<LogDto> logDtoListByUserId = userActionsLogReaderService.getLogDtoListByUserIdPaged(id,page,pageSize);
        int totalPages = userActionsLogReaderService.getTotalPages(id,pageSize);

        model.addAttribute("logDtoListByUserId", logDtoListByUserId);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("currentPage",page);
        model.addAttribute("totalPages",totalPages);
        model.addAttribute("listedUser", userServices.getUserById(id));
        return "user_actions";
    }

    //Get to Signup Page
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
        userActionsLoggerService.logUserActions(userRepository.findByUsername(username).getId(), "USER CREATED", 0, null, "Successful");
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
        userActionsLoggerService.logUserActions(updatedUserEntity.getId(), "DEPOSIT", amount, null, "successful");
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
            userActionsLoggerService.logUserActions(updatedUserEntity.getId(), "WITHDRAW", amount, null, "successful");
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
            userActionsLoggerService.logUserActions(updatedUserEntity.getId(), "TRANSFER", amount, receiverId, "successful");
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
        UserEntity loggedInUserEntity = (UserEntity) httpSession.getAttribute("loggedInUser");
        userActionsLoggerService.logUserActions(loggedInUserEntity.getId(), "LOGOUT", 0, null, "Successful");
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