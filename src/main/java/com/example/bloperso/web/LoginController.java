package com.example.bloperso.web;


import com.example.bloperso.Entities.Blogger;
import com.example.bloperso.Service.BloggerService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class LoginController {


    @Autowired
    private BloggerService bloggerService;


    private boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || AnonymousAuthenticationToken.class.
                isAssignableFrom(authentication.getClass())) {
            return false;
        }
        return authentication.isAuthenticated();
    }

    @GetMapping("/login")
    public String Login() {

            if(isAuthenticated())   return "redirect:/";
            return "login";


    }

    @GetMapping("/registration")
    public String registrationForm(Model model) {

        if(isAuthenticated())   return "redirect:/";

        Blogger blogger = new Blogger();
        model.addAttribute("bloggerDto", blogger);
        return "register";
    }
    @PostMapping("/registration")
    public String registerBlogger(Blogger blogger,
                                  Model model, BindingResult result, RedirectAttributes redirectAttributes) {
        bloggerService.reg(blogger);
        if (result.hasErrors()) {
            return "register";
        }

        // Save the user (or any other business logic)

        // Add success message
        redirectAttributes.addFlashAttribute("successMessage", "User registered successfully!");
        model.addAttribute("success", true);
        return "redirect:/login";
    }


}