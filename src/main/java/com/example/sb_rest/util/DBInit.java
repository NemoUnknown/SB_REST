package com.example.sb_rest.util;

import com.example.sb_rest.model.Role;
import com.example.sb_rest.model.User;
import com.example.sb_rest.service.RoleService;
import com.example.sb_rest.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;


@Component
public class DBInit {
    private final RoleService roleService;
    private final UserService userService;

    @Autowired
    public DBInit(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }
    @PostConstruct
    private void postConstruct() {

        roleService.init();

        Role adminRole = new Role (1L, "ROLE_ADMIN");
        Role userRole = new Role (2L, "ROLE_USER");

        Set<Role> adminSet = new HashSet<>();
        adminSet.add(adminRole);
        adminSet.add(userRole);

        Set<Role> userSet = new HashSet<>();
        userSet.add(userRole);

        User admin = new User();
        admin.setUsername("admin@mail.ru");
        admin.setPassword("admin");
        admin.setFirstName("Иван");
        admin.setLastName("Иванов");
        admin.setAge(28);
        admin.setRole(adminSet);

        User user = new User();
        user.setUsername("user@mail.ru");
        user.setPassword("user");
        user.setFirstName("Петр");
        user.setLastName("Петров");
        user.setAge(18);
        user.setRole(userSet);

        userService.addUser(admin);
        userService.addUser(user);
    }
}
