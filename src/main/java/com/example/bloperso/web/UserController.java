package com.example.bloperso.web;

import com.example.bloperso.Entities.Blogger;
import com.example.bloperso.Entities.Post;
import com.example.bloperso.Entities.PostCategorie;
import com.example.bloperso.Service.BloggerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {

    @Autowired
   private BloggerService bloggerService;



    private static Long idBlogger;

    public static void SetupB(Long i){
        idBlogger=i;
    }



    @PostMapping(value = "/bookMark")
    public String bookMark(@RequestParam(name = "id")Long id){
       Boolean b = bloggerService.BookMark(id,idBlogger);
       return "redirect:/";
    }

    //get BookMarks of connected user
    @RequestMapping(value = "/BookMarks")
    public String GetBookmarks(Model model){
        List<Post> posts = bloggerService.Bookmarked();
        model.addAttribute("poste",posts);
        model.addAttribute("image","images/img.jpg");
        return "index";
    }
    @RequestMapping(value = "/addFriendRequest/{id}")
    public String AddFriend(@RequestHeader(value = HttpHeaders.REFERER, required = false) final String referrer, @PathVariable Long id , Model model) {
        String message = bloggerService.AddFriendReq(id);

         model.addAttribute("message" , message);
        return "redirect:"+referrer;
    }
    @RequestMapping(value = "/myPosts")
    public String myPosts(Model model){
        List<Post> posts = bloggerService.ownPosts(idBlogger);
        return "ownPosts";
    }
    @RequestMapping(value="/profile/{id}")
    public String profile(@PathVariable Long id, Model model){

        Blogger b  = bloggerService.getBloggerInfo(id);
       model.addAttribute("blogger",b);
       model.addAttribute("posts",bloggerService.ownPosts(id));
       model.addAttribute("cat" , PostCategorie.values());
       model.addAttribute("catCount",bloggerService.getCountC(id));
        model.addAttribute("message","welcome to my page");
       return "profile";
    }

    @GetMapping("/addF/{id}")
    public String addFriend(@PathVariable Long id){
        bloggerService.becomeFriends(id);
        return  "redirect:/" ;
    }

    @GetMapping("/delF/{id}")
    public String delFriendRequest(@PathVariable Long id){
        bloggerService.refuseRequest(id);
        return  "redirect:/" ;
    }





}
