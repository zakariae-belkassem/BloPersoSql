package com.example.bloperso.web;


import com.example.bloperso.Service.BloggerService;
import com.example.bloperso.dto.BloggerDto;
import com.example.bloperso.dto.LoginDto;
import com.example.bloperso.dto.LoginDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class LoginController {

    @Autowired
    private BloggerService bloggerService;

    @GetMapping("/login")
    public String Login() {
        return "login";
    }

    @GetMapping("/registration")
    public String registrationForm(Model model) {
        BloggerDto blogger = new BloggerDto();
        model.addAttribute("bloggerDto", blogger);
        return "register";
    }
    @PostMapping("/registration")
    public String registerBlogger(BloggerDto bloggerDto, Model model) {
        bloggerService.reg(bloggerDto);
        model.addAttribute("success", true);
        return "register";
    }

}