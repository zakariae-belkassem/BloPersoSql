package com.example.bloperso.web;

import com.example.bloperso.Entities.Blogger;
import com.example.bloperso.Entities.Comment;
import com.example.bloperso.Service.BloggerService;
import com.example.bloperso.Service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CommentController {

    private Long idBlogger = new SessionService().idB();



    @Autowired
    BloggerService bloggerService ;
    @PostMapping(value = "/addCom")
    public String  AddCom(@RequestParam(name = "id") Long id, @ModelAttribute Comment cmt){
        System.out.println(id);
        bloggerService.comment(cmt,idBlogger,id);

        return "redirect:/post/"+id;
    }
}
