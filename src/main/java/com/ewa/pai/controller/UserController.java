package com.ewa.pai.controller;

import com.ewa.pai.entity.User;
import com.ewa.pai.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

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
        if(userRepository.findByLogin(principal.getName())!=null){
            //dodanie do modelu obiektu user - aktualnie zalogowanego użytkownika:
            m.addAttribute("user", userRepository.findByLogin(principal.getName()));
            //zwrócenie nazwy widoku profilu użytkownika - profile.html
            return "profile";
        }
        else {
            return "login";
        }

    }

    @GetMapping("/users")
    public String getUsers(Model m){
        m.addAttribute("users", userRepository.findAll());
        return "users";
    }
//
//    @GetMapping("/deleteUserById")
//    public String deleteUser(Model m){
//        m.addAttribute("user", new User());
//        return "deleteUserById";
//    }
//
//    @PostMapping("/deleteUser")
//    public String deleteUser(@ModelAttribute(value = "user") User user){
//        userRepository.deleteById(user.getUserid());
//        return "redirect:/users";
//    }


    @PostMapping("/edit")
    public String edytuj(Model m, @ModelAttribute(value = "user") User user, @RequestParam(value="action", required=false) String action, @RequestParam(value="delete", required=false) Integer delete){
        if(action!=null){
            User user1 = userRepository.findByLogin(action);
            m.addAttribute("user", user1);
            System.out.println(action);
            return "edit";
        }
        else{
            if(delete!=null){
                userRepository.deleteById(delete);
                return "redirect:/users";
            }
        }
        return "redirect:/users";
    }

    @PostMapping("/edit1")
    public String edit1(@ModelAttribute(value = "user") User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "redirect:/users";
    }

   //definicja metody, która zwróci do widoku users.html listę użytkowników z b

}
