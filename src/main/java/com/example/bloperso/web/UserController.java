package com.example.bloperso.web;

import com.example.bloperso.Entities.Post;
import com.example.bloperso.Entities.PostCategorie;
import com.example.bloperso.Service.BloggerService;
import com.example.bloperso.dto.BloggerDto;
import com.example.bloperso.dto.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    private DtoMapper dtoMapper;
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
    @RequestMapping(value="/profile/{id}")
    public String profile(@PathVariable Long id, Model model){
       BloggerDto b = bloggerService.getBloggerDto(idBlogger);

        List<Post> postList = bloggerService.ownPosts(idBlogger);
       model.addAttribute("blogger",b);
       model.addAttribute("posts",bloggerService.ownPosts(idBlogger));
       model.addAttribute("cat" , PostCategorie.values());
       model.addAttribute("catCount",bloggerService.getCountC(idBlogger));

       return "profile";
    }




}
