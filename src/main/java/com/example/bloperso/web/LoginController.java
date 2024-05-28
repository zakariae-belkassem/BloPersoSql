package com.example.bloperso.web;


import com.example.bloperso.Entities.Blogger;
import com.example.bloperso.Service.BloggerService;
import com.example.bloperso.dto.BloggerDto;
import com.example.bloperso.dto.LoginDto;
import com.example.bloperso.dto.LoginDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class LoginController {


    @Autowired
    private BloggerService bloggerService;
    @Autowired
    private DaoAuthenticationProvider daoAuthenticationProvider;



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
    public String registerBlogger(Blogger blogger,
                                  Model model) {
        bloggerService.reg(blogger);
        model.addAttribute("success", true);
        return "redirect:/login";
    }
//    @PostMapping("/authenticate")
//    public String authenticate(HttpServletRequest request,
//                               HttpServletResponse response,
//                               RedirectAttributes redirectAttributes){
//        String username=request.getParameter("username");
//        String password = request.getParameter("password");
//        System.out.println(password+"------"+username);
//        Optional<Blogger> userEntity = Optional.ofNullable(bloggerService.getByUsername(username));
//        System.out.println(userEntity.get().getPassword());
//        if(userEntity.isEmpty()) {
//            redirectAttributes.addAttribute("error","invalid username");
//            return "redirect:/login";
//        }
//        UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken.
//                unauthenticated(username,password);
//
//        Authentication authentication = daoAuthenticationProvider.authenticate(token);
//        SecurityContext context = SecurityContextHolder.createEmptyContext();
//
//        context.setAuthentication(authentication);
//        new HttpSessionSecurityContextRepository().saveContext(context, request, response);
//
//
//
//
//        redirectAttributes.addAttribute("success", "You have been logged in successfully");
//        System.out.println("dkhel");
//        return "redirect:/";
//    }
}