package org.tchss.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.tchss.model.User;
import org.tchss.service.UserService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ApplicationController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping("login")
    public String login() {
        return "login";
    }

    @GetMapping("register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("register")
    public String register(@RequestParam("email") String email,
                           @RequestParam("password") String password,
                           @RequestParam("firstName") String firstName,
                           @RequestParam("lastName") String lastName,
                           Model model) {
        User user = new User();
        user.setPassword(password);
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);

        user = userService.registerUser(user);

        model.addAttribute("user", user);
        model.addAttribute("newlyRegistered", true);

        return "login";
    }

    @GetMapping("forgot-password")
    public String forgotPassword() {
        return "forgot_password";
    }

    @PostMapping("forgot-password")
    public String forgotPassword(@RequestParam("email") String email,
                                 Model model) {

        userService.generatePasswordResetToken(email);

        model.addAttribute("message", "If the email address links to an existing account, then a password email was sent");

        return "forgot_password";
    }

    @GetMapping("password-reset/{token}")
    public String passwordReset(@PathVariable String token,Model model) {
        model.addAttribute("token", token);
        return "password_reset";
    }

    @PostMapping("password-reset")
    public String passwordReset(@RequestParam("password") String password,
                                @RequestParam("token") String token,
                                Model model) {
        userService.passwordReset(token, password);
        model.addAttribute("message", "Password successfully reset, please login.");
        return "login";
    }

    @GetMapping("test")
    public String test() {
        return "index";
    }
}
