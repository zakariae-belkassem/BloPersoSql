package com.example.bloperso.web;

import com.example.bloperso.Entities.Blogger;
import com.example.bloperso.Entities.Post;
import com.example.bloperso.Entities.PostCategorie;
import com.example.bloperso.Entities.Visibilite;
import com.example.bloperso.Service.BloggerService;

import com.example.bloperso.dao.BloggerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@Controller
public class UserController {

    @Autowired
   private BloggerService bloggerService;

    @Autowired
    private BloggerRepository bloggerRepository;



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
        model.addAttribute("blogger",bloggerService.getBloggerInfo(idBlogger));
        if (bloggerRepository.findById(idBlogger).get().getFriends()!=null) model.addAttribute("hasReq",true);
        else model.addAttribute("hasReq",false);
        model.addAttribute("cat",PostCategorie.values());
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

        return "ownPosts";
    }
    @RequestMapping(value="/profile/{id}")
    public String profile(@PathVariable Long id, Model model){

        Blogger b  = bloggerService.getBloggerInfo(id);
       model.addAttribute("blogger",b);
       model.addAttribute("posts",bloggerService.ownPosts(id));
       model.addAttribute("publicposts",bloggerService.ownPosts(id).stream().filter(e->e.getVisibilite().equals(Visibilite.Public)).toList());
        model.addAttribute("friendlyposts",bloggerService.ownPosts(id).stream().filter(e->e.getVisibilite().equals(Visibilite.Friends_only)).toList());

       model.addAttribute("cat" , PostCategorie.values());
       model.addAttribute("catCount",bloggerService.getCountC(id));
        model.addAttribute("message","welcome to my page");
        model.addAttribute("Lblogger",bloggerService.getBloggerInfo(idBlogger));
        if (Objects.equals(idBlogger, id)) model.addAttribute("isEditable",true);
        else model.addAttribute("isEditable",false);
       return "profile";
    }
    @PostMapping("/profile/update")
    public String updateP(@RequestParam(name = "fullName" ) String fn ,@RequestParam(name = "adresse") String ad ,@RequestParam(name="email") String un ,@RequestParam(name="phoneNumber") String pn , Model model){

        Blogger b = bloggerService.getBloggerInfo(idBlogger);
       b.setAdresse(ad);
       b.setEmail(un);
       b.setFullName(fn);
       b.setPhoneNumber(pn);
       bloggerService.update(b);
       return "redirect:/profile/"+idBlogger;
    }

    @RequestMapping(value = "/myprofile")
    public String myProfile(Model model){

        model.addAttribute("Lblogger",bloggerService.getBloggerInfo(idBlogger));
        return "redirect:/profile/"+idBlogger;
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
