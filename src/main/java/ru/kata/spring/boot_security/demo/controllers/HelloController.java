package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HelloController {

    @GetMapping("/index")
    public String printWelcome(ModelMap model) {
        List<String> messages = new ArrayList<>();
        String unicodeSmile = "\uD83D\uDE0A";
        String unicodeDance = "\uD83D\uDC83";
        messages.add("Hello!");
        messages.add("I am first Spring Security application");
        messages.add(unicodeDance + unicodeSmile + unicodeDance);
        model.addAttribute("messages", messages);
        return "index";
    }
    @GetMapping("/showUserInfo")
    public String showUserInfo(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        User user= (User) authentication.getPrincipal();
        System.out.println(user.toString());
        return "index";
    }
}