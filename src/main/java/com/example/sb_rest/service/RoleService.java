package com.example.sb_rest.service;

import com.example.sb_rest.model.Role;
import com.example.sb_rest.model.User;

import java.util.Set;

public interface RoleService {

    Set<Role> getRoles();
    Role getByName(String name);
    void addRole(Role role);
    Role findById(Long id);
    Set<Role> getCurrentRoles(User user);
    void init();
}
