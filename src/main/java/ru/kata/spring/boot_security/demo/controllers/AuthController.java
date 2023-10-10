package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RegistrationService;
import ru.kata.spring.boot_security.demo.util.UserValidator;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AuthController {
    private final UserValidator userValidator;
    private final RegistrationService registrationService;

    @Autowired
    public AuthController(UserValidator userValidator, RegistrationService registrationService) {
        this.userValidator = userValidator;
        this.registrationService = registrationService;
    }

    @GetMapping("/")
    public String printNo(ModelMap model) {
        List<String> messages = new ArrayList<>();
        String unicodeEyes = "\uD83D\uDE22";
        messages.add("Hello! You need to login");
        messages.add(unicodeEyes + unicodeEyes);
        model.addAttribute("messages", messages);
        return "index";
    }
    @GetMapping("/auth/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/auth/registration")
    public String registrationPage(@ModelAttribute("user") User user) {
        return "auth/registration";
    }

    @PostMapping("/auth/registration")
    public String performRegistration(@ModelAttribute("user") @Valid User user,
                                      BindingResult blindingResult) {
        userValidator.validate(user, blindingResult);
        if (blindingResult.hasErrors())
            return "/auth/registration";
        registrationService.register(user);
        return "redirect:/auth/login";
    }
}
