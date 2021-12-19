package com.ewa.pai.repository;

import com.ewa.pai.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    public User findByLogin(String login);
}
