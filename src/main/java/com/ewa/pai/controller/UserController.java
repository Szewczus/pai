package com.ewa.pai.controller;

import com.ewa.pai.entity.User;
import com.ewa.pai.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String loginPage() {
        //zwrócenie nazwy widoku logowania - login.html
        return "login";
    }
    @GetMapping("/register")
    public String registerPage(Model m) {
        //dodanie do modelu nowego użytkownika
        m.addAttribute("user", new User());
        //zwrócenie nazwy widoku rejestracji - register.html
        return "register";
    }
    @PostMapping("/register")
    public String registerPagePOST(@ModelAttribute(value = "user") User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        //przekierowanie do adresu url: /login
        return "redirect:/login";
    }
    @GetMapping("/profile")
    public String profilePage(Model m, Principal principal) {
        //dodanie do modelu obiektu user - aktualnie zalogowanego użytkownika:
        m.addAttribute("user", userRepository.findByLogin(principal.getName()));
        //zwrócenie nazwy widoku profilu użytkownika - profile.html
        return "profile";

    }

    @GetMapping("/users")
    public String getUsers(Model m){
        m.addAttribute("users", userRepository.findAll());
        return "users";
    }

   //definicja metody, która zwróci do widoku users.html listę użytkowników z b

}
