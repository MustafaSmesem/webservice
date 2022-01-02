package com.joumer.webservice.controller;

import com.joumer.webservice.config.security.JwtService;
import com.joumer.webservice.document.Role;
import com.joumer.webservice.document.User;
import com.joumer.webservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

    public UserController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }


    @GetMapping(value = "/getAll", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN_USER')")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.getAll());
    }


    @PostMapping(value = "/register", produces = "application/json")
    public ResponseEntity<?> register(@RequestBody User formUser) {
        var user = new User();
        user.setName(formUser.getName());
        user.setSurname(formUser.getSurname());
        user.setUsername(formUser.getUsername());
        user.setAdmin(formUser.isAdmin());
        if (formUser.getPassword() != null && !formUser.getPassword().equals("") && !formUser.getPassword().isEmpty()) {
            user.setPassword(BCrypt.hashpw(formUser.getPassword(), BCrypt.gensalt()));
        }
        var roles = new ArrayList<Role>();
        roles.add(new Role("STANDARD_USER"));
        if (formUser.isAdmin()) roles.add(new Role("ADMIN_USER"));
        user.setRoles(roles);
        userService.saveUser(user);
        return jwtService.authenticate(formUser.getUsername(), formUser.getPassword());
    }

    @PostMapping(value = "/update", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN_USER')")
    public ResponseEntity<?> updateUser(@RequestBody User formUser) {
        var user = userService.saveUser(formUser);
        return ResponseEntity.ok(user);
    }


    @GetMapping(value = "/get/{itemId}", produces = "application/json")
    @PreAuthorize("hasAuthority('ADMIN_USER')")
    public User getUser(@PathVariable("itemId") String itemId) {
        User user = userService.getUserById(itemId);
        user.setPassword("****");
        return user;
    }

}
