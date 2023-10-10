package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String printHelloAdmin() {
        return "helloAdmin";
    }
    @GetMapping("/showAll")
    public String showAllUsers(
            @RequestParam(name = "count", defaultValue = "-1") int count,
            Model model) {
        List<User> userList = userService.showAllUsers();
        if ((count == -1) || (count >= userList.size())) {
            model.addAttribute("users", userList);
        } else {
            model.addAttribute("users", userList.subList(0, count));
        }
        return "showAllUsersToAdmin";
    }
    @GetMapping("/{id}")
    public String showOneUser(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.showOneUser(id));
        return "showUserInfoToAdmin";
    }


//

    @GetMapping("/new")
    public String createNewUser(@ModelAttribute("user") User user) {
        return "createNewUser";
    }

    @PostMapping()
    public String saveNewUser(@ModelAttribute("user") @Valid User user,
                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "createNewUser";
        }
        userService.save(user);
        return "redirect:/admin";
    }


    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.showOneUser(id));
        return "editToAdmin";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult, @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "edit";
        }
        userService.update(id, user);
        return "redirect:/showAllUsersToAdmin";
    }


    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        userService.delete(id);
        return "redirect:/admin";
    }

    @GetMapping("/get/{userId}")
    public String getUser(@PathVariable("userId") Integer userId, Model model) {
        model.addAttribute("allUsers", userService.showOneUser(userId));
        return "helloAdmin";
    }
}