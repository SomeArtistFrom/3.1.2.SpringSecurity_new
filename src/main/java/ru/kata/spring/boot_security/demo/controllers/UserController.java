package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.MyUserDetailsService;
import ru.kata.spring.boot_security.demo.services.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final MyUserDetailsService myUserDetailsService;

    @Autowired
    public UserController(UserService userService, MyUserDetailsService myUserDetailsService) {
        this.userService = userService;
        this.myUserDetailsService = myUserDetailsService;
    }

    @GetMapping()
    public String user(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User userSecurity =
                (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        User user = myUserDetailsService.findByUsername(userSecurity.getUsername());

        if (user != null) {
            model.addAttribute("user", userService.showOneUser(user.getId()));
            System.out.println(user.toString());
            return "user";
        } else return "Пользователя не существует";
    }
}