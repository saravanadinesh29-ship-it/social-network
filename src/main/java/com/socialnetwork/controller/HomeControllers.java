package com.socialnetwork.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {
    
    @GetMapping("/")
    public String home(HttpSession session) {
        if (session.getAttribute("user") != null) {
            return "feed"; // Logged in users see feed
        }
        return "index"; // Guests see login page
    }
    
    @GetMapping("/feed")
    public String feed() {
        return "feed";
    }
    
    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }
}