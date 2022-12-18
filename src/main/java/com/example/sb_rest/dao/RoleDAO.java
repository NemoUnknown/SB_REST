package com.example.sb_rest.dao;

import com.example.sb_rest.model.Role;

import java.util.Set;

public interface RoleDAO {
    Set<Role> getRoles();
    Role getByName(String name);
    void addRole(Role role);
    Role findById(Long id);
}
