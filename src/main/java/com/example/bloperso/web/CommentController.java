package com.example.bloperso.web;

import com.example.bloperso.Entities.Blogger;
import com.example.bloperso.Entities.Comment;
import com.example.bloperso.Service.BloggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CommentController {

    @Autowired
    BloggerService bloggerService ;
    @PostMapping(value = "/addCom")
    public String  AddCom(@RequestParam(name = "id") Long id, @ModelAttribute Comment cmt){
        bloggerService.comment(cmt,1L,id);

        return "redirect : /post/"+id;
    }
}
