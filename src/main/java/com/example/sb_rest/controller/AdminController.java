package com.example.sb_rest.controller;

import com.example.sb_rest.model.User;
import com.example.sb_rest.service.RoleService;
import com.example.sb_rest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("")
    public String showMainPage(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("admin", userService.getUser(user.getId()));
        model.addAttribute("listUsers", userService.listUsers());
        model.addAttribute("personalRole", user.getRoles());
        model.addAttribute("roles", roleService.getRoles());
        return "/viewsForAdmin/main";
    }

    @GetMapping("/infoPage")
    public String showInfoPage(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("admin", userService.getUser(user.getId()));
        model.addAttribute("role", user.getRoles());
        return "viewsForAdmin/info";
    }

}
