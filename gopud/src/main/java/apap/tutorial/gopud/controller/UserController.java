package apap.tutorial.gopud.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Pattern.Flag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import apap.tutorial.gopud.model.RoleModel;
import apap.tutorial.gopud.model.UserModel;
import apap.tutorial.gopud.service.UserDetailsServiceImpl;
import apap.tutorial.gopud.service.UserService;

import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping(value = "/update")
    public String updatePassword(){
        return "update-pass";
    }

    @PostMapping(value = "/update")
    public String submitPassword(HttpServletRequest request, Model model){
        UserModel user = userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Boolean equals = userService.isEquals(request.getParameter("oldPassword"), user.getPassword());

        if(equals){
            if(request.getParameter("newPassword").equals(request.getParameter("confirmPassword"))){
                user.setPassword(request.getParameter("newPassword"));
                userService.updatePassword(user);
                return "update-pass";
            }else{
                Boolean flag = true;
                model.addAttribute("notMatch", flag);
                return "update-pass";
            }
        }else{
            Boolean flag = true;
            model.addAttribute("notEquals", flag);
            return "update-pass";
        }
    }
    
}