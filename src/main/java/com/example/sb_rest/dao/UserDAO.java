package com.example.sb_rest.dao;

import com.example.sb_rest.model.User;

import java.util.List;

public interface UserDAO {
    void addUser(User user);

    List<User> listUsers();

    User getUser(Long id);

    void editUser(User user);

    void deleteUser(Long id);

    User findByUsername(String username);
}
