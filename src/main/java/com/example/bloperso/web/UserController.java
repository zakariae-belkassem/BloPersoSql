package com.example.bloperso.web;

import com.example.bloperso.Entities.Post;
import com.example.bloperso.Service.BloggerService;
import com.example.bloperso.dao.BloggerRepository;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class UserController {
    @Autowired
   private BloggerService bloggerService;
    private Long idBlogger = 1L;

//handling register and login
    //------------------
    //handle profile managment (modifications and what not )

    @PostMapping(value = "/bookMark")
    public String bookMark(@RequestParam(name = "id")Long id){
       Boolean b = bloggerService.BookMark(id,idBlogger);
        if (b) return "redirect:/";
        else return "redirect:/BookMarks";
    }
    //get BookMarks of connected user
    @RequestMapping(value = "/BookMarks")
    public String GetBookmarks(Model model){
        List<Post> posts = bloggerService.Bookmarked();
        model.addAttribute("poste",posts);
        model.addAttribute("image","images/img.jpg");
        return "indexx";
    }
    @RequestMapping(value = "/addFriend")
    public String AddFriend(@RequestParam(name = "id")Long id){
        bloggerService.AddFriend(id);

        return "redirect:/";
    }
    @RequestMapping(value = "/myPosts")
    public String myPosts(Model model){
        List<Post> posts = bloggerService.ownPosts(idBlogger);
        return "ownPosts";
    }

}
