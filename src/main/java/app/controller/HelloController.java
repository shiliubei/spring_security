package app.controller;


import app.model.Role;
import app.model.User;
import app.service.UserService;
import app.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class HelloController {

    public final UserService userService;

    public final RoleService roleService;

    @Autowired
    public HelloController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/login")
    public String listCustomers() {

        return "login";
    }

    @GetMapping("/admin/Add")
    public String addUser() {
        return "adminAdd";
    }

    @GetMapping("/admin/delete")
    public String deleteUser(@RequestParam("id") Integer id) {
        userService.deleteUserById(id);
        return "redirect:/admin/UserList";
    }

    @GetMapping("/admin/edit")
    public String updateUser(@RequestParam("id") Integer id, Model model) {
        User user = userService.getUser(id);
        model.addAttribute("user", user);
        return "editUser";
    }

    @PostMapping("/admin/edit")
    public String updateUser(@ModelAttribute("user") User user, String role) {

        String[] rolesArray;
        rolesArray = role.split(",");
        Set<Role> roles = new HashSet<>();
        for (String elem : rolesArray) {
            Role userRole = roleService.getRoleByName(elem);
            roles.add(userRole);
        }
        user.setRoles(roles);

        userService.updateUser(user);
        return "redirect:/admin/UserList";
    }

    @GetMapping("/admin/UserList")
    public String listCustomers(Model theModel) {
        List<User> usersList = userService.getAllUsers();
        theModel.addAttribute("usersFromServer", usersList);
        return "usersList";
    }

    @PostMapping("/admin/Add")
    public String saveUser(@ModelAttribute("user") User user, String role) {

        String[] rolesArray;
        rolesArray = role.split(",");
        Set<Role> roles = new HashSet<>();
        for (String elem : rolesArray) {
            Role userRole = roleService.getRoleByName(elem);
            roles.add(userRole);
        }
        user.setRoles(roles);
        userService.addUser(user);
        return "redirect:/admin/UserList";
    }

    @GetMapping("/user")
    public String userPage() {
        return "userPage";
    }

}
