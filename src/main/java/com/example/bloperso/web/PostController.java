package com.example.bloperso.web;

import com.example.bloperso.Entities.Blogger;
import com.example.bloperso.Entities.Post;
import com.example.bloperso.Entities.PostCategorie;
import com.example.bloperso.Entities.Visibilite;
import com.example.bloperso.Service.BloggerService;
import com.example.bloperso.Service.PostService;
import com.example.bloperso.Service.SessionService;
import com.example.bloperso.dao.PostRepository;
import com.example.bloperso.dto.DtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

@Controller
public class    PostController {

    @Autowired
    BloggerService bloggerService;
    @Autowired
    private PostRepository postRepository ;
    @Autowired
    private PostService postService;


    private Long idBlogger =  new SessionService().idB();
    @RequestMapping(value = "/")
    public String index(Model model){
            List<Post> posts = postRepository.findAll();
            model.addAttribute("poste",posts);
            model.addAttribute("image","images/img.jpg");
            model.addAttribute("featured",postService.Featured());
        return "index";
    }

    @GetMapping("/add")
    public String showAddPostForm(Model model) {

        model.addAttribute("categories", PostCategorie.values());
        model.addAttribute("visibilite",Visibilite.values());
        return "addPost";
    }

    // Method to handle form submission and save post data
    @PostMapping("/add")
    public String addPost(@ModelAttribute("newPost") Post newPost, @RequestParam("visibilite") Visibilite visibilite, @RequestParam("file")MultipartFile file) {
        try {
            Blob blob ;
            if (!file.isEmpty()) {
                blob = new SerialBlob(file.getBytes());
                newPost.setImg(blob);
            }
            newPost.setBlogger(bloggerService.getBloggerInfo(idBlogger));
            postRepository.save(newPost);
        } catch (IOException | SQLException e) {
           //
        }
        return "redirect:/"; // Redirect back to the home page
    }
    @PostMapping(value = "/like")
    public String LikeP(@RequestParam(name = "id")Long id){

        Boolean b =bloggerService.like(id,idBlogger);
        System.out.println(id);
        return "redirect:/";
    }
//popular posts + if not connected show preview only -> on click loginPage + Sessions + Security

    @GetMapping(value = "/deleteP/{id}")
    public String removeP(@PathVariable Long id){
        bloggerService.removePost(id);
        return "redirect : /";
    }

    @GetMapping(value = "/ownPosts")
    public String ownP(Model model){

        model.addAttribute("posts",bloggerService.ownPosts(idBlogger));
        return "CrudPost";
    }
    @GetMapping(value = "/modP/{id}")
    public String Modifier(@PathVariable Long id,Model model){
       model.addAttribute("post", postService.getPostById(id));
        return "modifier";
    }
    @PostMapping (value = "/modP")
    public String Modifier(@ModelAttribute("post") Post p ){
        postService.createPost(p);
        return "redirect:/";
    }


}


