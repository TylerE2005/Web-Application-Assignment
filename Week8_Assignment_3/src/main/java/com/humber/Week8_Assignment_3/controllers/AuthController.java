package com.humber.Week8_Assignment_3.controllers;

import com.humber.Week8_Assignment_3.models.ItemUser;
import com.humber.Week8_Assignment_3.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller //Created folder for authentication methods
public class AuthController implements ErrorController {

    private final UserService userService;
    public AuthController(UserService userService) { this.userService = userService; }

    @Value("${store.name}")
    private String storeName;

    //Brings to custom error endpoint
    @GetMapping("/error")
    public String handleError() {
        return "auth/error";
    }

    //Brings to custom login endpoint
    @GetMapping("/login")
    public String login(Model model, @RequestParam(required = false) String message) {
        model.addAttribute("storeName", storeName);
        model.addAttribute("message", message);
        return "auth/login";
    }
    //Custom logout endpoint
    @GetMapping("/logout")
    public String customLogout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        //perform the logout logic
        new SecurityContextLogoutHandler().logout(request, response, authentication); //Logs out user
        return "redirect:/login?message=You've been logged out successfully"; //redirects to custom login page with success message
    }

    //open custom registration page
    @GetMapping("/register")
    public String register(Model model, @RequestParam(required = false) String message) {
        model.addAttribute("message", message);
        model.addAttribute("user", new ItemUser() );
        return "auth/register";
    }
    //register(post)
    @PostMapping("/register")
    public String register(@ModelAttribute ItemUser user) {
        int saveUserCode = userService.saveUser(user);

        //If user already exists in database, redirect to register page with error message
        if(saveUserCode == 0) {
            return "redirect:/register?message=User already exists";
        }

        return "redirect:/login?message=Registration Successful, Login to Continue";
    }
}
