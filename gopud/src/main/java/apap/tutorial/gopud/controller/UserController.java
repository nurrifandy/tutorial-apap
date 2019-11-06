package apap.tutorial.gopud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import apap.tutorial.gopud.model.RoleModel;
import apap.tutorial.gopud.model.UserModel;
import apap.tutorial.gopud.service.UserService;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequestMapping("/user")
public class UserController{
    @Autowired
    private UserService userService;

    @PostMapping(value="/addUser")
    public String addUserSubmit(@ModelAttribute UserModel user) {
        //TODO: process POST request
        userService.addUser(user);
        return "home";
    }
    
}