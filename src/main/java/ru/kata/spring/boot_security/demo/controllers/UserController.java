package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;

import static org.springframework.security.authorization.AuthorityReactiveAuthorizationManager.hasRole;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String showUserInfoToUser(Model model){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        User user= (User) authentication.getPrincipal();

        model.addAttribute("user", userService.showOneUser(user.getId()));
        System.out.println(user.toString());
        return "showUserInfoToUser";
    }
}