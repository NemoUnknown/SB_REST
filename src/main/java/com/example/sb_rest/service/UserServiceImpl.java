package com.example.sb_rest.service;

import com.example.sb_rest.dao.UserDAO;
import com.example.sb_rest.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    public final UserDAO userDAO;
    public final RoleService roleService;
    public final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, RoleService roleService, BCryptPasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(roleService.getCurrentRoles(user));
        userDAO.addUser(user);
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> listUsers() {
        return userDAO.listUsers();
    }

    @Transactional(readOnly = true)
    @Override
    public User getUser(Long id) {
        Optional<User> user = Optional.ofNullable(userDAO.getUser(id));
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UsernameNotFoundException(String.format("Пользователь с id '%d' не найден!", id));
        }
    }

    @Transactional(readOnly = true)
    @Override
    public User findByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    @Transactional
    @Override
    public void editUser(Long id, User user) {
        User editUser = userDAO.getUser(id);
        editUser.setFirstName(user.getFirstName());
        editUser.setLastName(user.getLastName());
        editUser.setUsername(user.getUsername());
        editUser.setAge(user.getAge());

        if (user.getPassword().equals("") || user.getPassword().equals(editUser.getPassword())) {
            editUser.setPassword(editUser.getPassword());
        } else {
            editUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        if (user.getRoles() == null) {
            editUser.setRole(editUser.getRoles());
        } else {
            editUser.setRole(roleService.getCurrentRoles(user));
        }

        userDAO.editUser(editUser);
    }

    @Transactional
    @Override
    public void deleteUser(Long id) {
        userDAO.deleteUser(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = Optional.ofNullable(userDAO.findByUsername(username));
        if (user.isEmpty()) {
            throw new UsernameNotFoundException(String.format("Пользователь '%s' не найден!", username));
        }
        return user.get();
    }
}
