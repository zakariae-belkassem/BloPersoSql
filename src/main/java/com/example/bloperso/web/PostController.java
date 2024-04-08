package com.example.bloperso.web;

import com.example.bloperso.Entities.Post;
import com.example.bloperso.Entities.Visibilite;
import com.example.bloperso.dao.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class PostController {
    @Autowired
    private PostRepository postRepository ;

    @RequestMapping(name = "")
    public String index(Model model){
        List<Post> posts = postRepository.findAll().stream().filter(e->e.getVisibilite()==Visibilite.Public).toList();
        model.addAttribute("poste",posts);
        return "index";
    }
}
