package com.example.bloperso.web;

import com.example.bloperso.Entities.Blogger;
import com.example.bloperso.Entities.Post;
import com.example.bloperso.Entities.PostCategorie;
import com.example.bloperso.Entities.Visibilite;
import com.example.bloperso.Service.BloggerService;
import com.example.bloperso.Service.PostService;
import com.example.bloperso.dao.BloggerRepository;
import com.example.bloperso.dao.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

import java.util.List;


@Controller
public class  PostController {

    @Autowired
    private  BloggerService  bloggerService;
    @Autowired
    private PostRepository postRepository ;
    @Autowired
    private PostService postService;
    private static Long idBlogger;
    @Autowired
    private BloggerRepository bloggerRepository;


    public   void  SetUpBlogger(){
        String principalName ;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        principalName = authentication.getName();
        Long id =bloggerService.getByUsername(principalName).getId();
        idBlogger=id;
        BloggerService.SetupB(id);
        CommentController.SetupB(id);
        UserController.SetupB(id);
    }


    // This method handles requests to display a post detail page
    @GetMapping("/post/{postId}")
    public String showPostDetail(@PathVariable("postId") Long postId, Model model) {

        Post post = postService.getPostById(postId);

        Blogger b =bloggerRepository.findById(idBlogger).orElse(null);
        model.addAttribute("post", post);
        model.addAttribute("image","images/img.jpg");
        model.addAttribute("Lblogger",b);
        model.addAttribute("isFriends",(b.getFriends().contains(post.getBlogger())));
        model.addAttribute("similiarposts",postService.publicPosts().stream().filter(e->e.getTheme().equals(post.getTheme()) && !e.equals(post)));

        return "post";
    }
    @GetMapping(value = "/categorie/{topic}")
    public String showTopic(Model model , @PathVariable String topic) {

        model.addAttribute("poste",postService.FilterByTopic(topic));
        model.addAttribute("blogger",bloggerService.getBloggerInfo(idBlogger));
        model.addAttribute("cat" , PostCategorie.values());
        model.addAttribute("featured",postService.Featured());
        model.addAttribute("blogger",bloggerService.getBloggerInfo(idBlogger));
        if (bloggerRepository.findById(idBlogger).get().getFriends()!=null) model.addAttribute("hasReq",true);
        else model.addAttribute("hasReq",false);

        return "index";
    }
    @GetMapping(value = "/search")
    public String search(@RequestParam("param") String search ,Model model){
        List<Post> result = postService.Searched(search);
        model.addAttribute("poste", result);
        model.addAttribute("blogger",bloggerService.getBloggerInfo(idBlogger));
        if (bloggerRepository.findById(idBlogger).get().getFriends()!=null) model.addAttribute("hasReq",true);
        else model.addAttribute("hasReq",false);
        model.addAttribute("cat",PostCategorie.values());
        return "index";
    }



    @RequestMapping(value = "/")
    public String index(Model model){
            //do session stuff
        SetUpBlogger();
            //index stuff
            List<Post> posts = postRepository.findAll();
            model.addAttribute("poste",posts.stream().filter(e->e.getVisibilite().equals(Visibilite.Public)).toList());
        model.addAttribute("blogger",bloggerService.getBloggerInfo(idBlogger));
        if (bloggerRepository.findById(idBlogger).get().getFriends()!=null) model.addAttribute("hasReq",true);
        else model.addAttribute("hasReq",false);
        model.addAttribute("cat",PostCategorie.values());
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
    public String addPost(@ModelAttribute("newPost") Post newPost, @RequestParam("file")MultipartFile file) {

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

        bloggerService.like(id,idBlogger);

        return "redirect:/";
    }
//popular posts + if not connected show preview only -> on click loginPage + Sessions + Security

    @GetMapping(value = "/deleteP/{id}")
    public String removeP(@PathVariable Long id){

        bloggerService.removePost(id);
        return "redirect:/";
    }

    @GetMapping(value = "/ownPosts")
    public String ownP(Model model){


        model.addAttribute("posts",bloggerService.ownPosts(idBlogger));
        return "CrudPost";
    }
    @GetMapping(value = "/ownPosts/{id}")
    public String ownPp(@PathVariable Long id,Model model){


        model.addAttribute("poste",bloggerService.ownPosts(id));
        return "index";
    }
    @GetMapping(value = "/modP/{id}")
    public String Modifier(@PathVariable Long id,Model model){

       model.addAttribute("post", postService.getPostById(id));
        return "modifier";
    }
    @PostMapping (value = "/modP")
    public String Modifier(@RequestParam(name = "title") String t ,@RequestParam(name = "subtitle") String st,@RequestParam(name = "corpsPost") String c,@RequestParam(name = "theme") PostCategorie cat , @RequestParam(name = "visibilite") Visibilite v , @RequestParam(name = "id") Long id,@RequestParam(name = "file") MultipartFile file ){
        Post p = postService.getPostById(id);

        p.setTitle(t);
        p.setSubtitle(st);
        p.setCorpsPost(c);
        p.setTheme(cat);
        p.setVisibilite(v);
        postService.modifyPost(p,file,idBlogger);
        return "redirect:/post/"+p.getId();
    }



}


