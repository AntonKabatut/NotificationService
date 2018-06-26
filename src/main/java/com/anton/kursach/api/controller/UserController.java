package com.anton.kursach.api.controller;

import com.anton.kursach.model.UserProfile;
import com.anton.kursach.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<UserProfile>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> saveUser(@RequestBody UserProfile userProfile) {
        userService.insertUser(userProfile);
        return new ResponseEntity<>("User registered", HttpStatus.OK);
    }

}
