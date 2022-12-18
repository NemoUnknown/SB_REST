package com.example.sb_rest.service;

import com.example.sb_rest.dao.RoleDAO;
import com.example.sb_rest.model.Role;
import com.example.sb_rest.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService{
    private final RoleDAO roleDAO;

    @Autowired
    public RoleServiceImpl(RoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<Role> getRoles() {return roleDAO.getRoles();}

    @Transactional(readOnly = true)
    @Override
    public Role getByName(String name) {
        return roleDAO.getByName(name);
    }

    @Transactional
    @Override
    public void addRole(Role role) {
        if (!role.getName().startsWith("ROLE_")) {
            role.setName("ROLE_" + role.getName());
        }
        roleDAO.addRole(role);
    }

    @Transactional(readOnly = true)
    @Override
    public Role findById(Long id) {
        return roleDAO.findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Set<Role> getCurrentRoles(User user) {
        List<Role> roleList = new ArrayList<>(user.getRoles());
        Optional<Role> userRole = Optional.ofNullable(roleDAO.getByName("ROLE_USER"));
        Optional<Role> adminRole = Optional.ofNullable(roleDAO.getByName("ROLE_ADMIN"));

        if (roleList.isEmpty()) {
            roleList = new ArrayList<>();
            roleList.add(userRole.orElse(new Role("ROLE_USER")));
        } else if ((roleList.size() == 1) && (roleList.get(0).getId() == 1L)) {
            roleList.add(userRole.orElse(new Role("ROLE_USER")));
        }

        return roleList.stream().collect(Collectors.toSet());
    }

    @Transactional
    @Override
    public void init() {
        roleDAO.addRole(new Role("ROLE_ADMIN"));
        roleDAO.addRole(new Role("ROLE_USER"));
    }
}
