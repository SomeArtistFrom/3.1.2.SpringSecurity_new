package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String showUser(Model model, Principal principal) {
        User user = userService.findUserByUsername(principal.getName());
        model.addAttribute("user", userService.showOneUser(user.getId()));
        return "showsInfoAboutOneUser";
    }

    @GetMapping("/profile/edit")
    public String editUserProfile(Model model, Principal principal) {
        User user = userService.findUserByUsername(principal.getName());
        model.addAttribute("user", userService.showOneUser(user.getId()));

        return "edit";
    }

    @PatchMapping("/profile")
    public String updateUserProfile(@ModelAttribute("user") @Valid User user,
                                    BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "edit";
        }
        String username = principal.getName();
        User existingUser = userService.findUserByUsername(username);
        userService.update(existingUser.getId(), existingUser);
        return "redirect:/users";
    }


    @DeleteMapping("/profile")
    public String deleteUserProfile(Principal principal) {
        User user = userService.findUserByUsername(principal.getName());
        userService.delete(user.getId());

        return "redirect:/logout";
    }
}