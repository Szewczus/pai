package com.ewa.pai;

import com.ewa.pai.entity.User;
import com.ewa.pai.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class PaiApplication {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(PaiApplication.class, args);
    }

    @PostConstruct
    public void init() {
        userRepository.save(new User("Piotr", "Piotrowski","admin", passwordEncoder.encode("admin")));
        userRepository.save(new User("Ania", "Annowska", "ania", passwordEncoder.encode("ania")));

    }
}
