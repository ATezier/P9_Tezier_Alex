package com.openclassrooms.microservice_ui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

@Controller
public class LoginController {
    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/")
    public String home() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String login(String username, String password) {
        restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor(username, password));
        return "redirect:/patient/list";
    }

    @GetMapping("/logout")
    public String logout() {
        restTemplate.getInterceptors().clear();
        return "redirect:/login";
    }
}
