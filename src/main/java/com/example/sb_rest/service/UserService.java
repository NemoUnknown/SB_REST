package com.example.sb_rest.service;

import com.example.sb_rest.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    void addUser(User user);

    List<User> listUsers();

    User getUser(Long id);

    User findByUsername(String username);

    void editUser(Long id, User user);

    void deleteUser(Long id);
}
