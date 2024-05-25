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
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/post")
public class
pController {

    public pController(PostService postService, BloggerService bloggerService) {
        this.postService = postService;
        this.bloggerService = bloggerService;
    }

    // This method handles requests to display a post detail page
    @GetMapping("/{postId}")
    public String showPostDetail(@PathVariable("postId") Long postId, Model model) {
        // Assuming you have a service layer that fetches the post details by postId
        Post post = postService.getPostById(postId);
//        if (post == null) {
//            // Handle case where post is not found
//            return "error"; // You can return an error page or redirect to another page
//        }

        // Add the post object to the model to be accessed in the Thymeleaf template
        model.addAttribute("post", post);
        model.addAttribute("image","images/img.jpg");
        return "post"; // This corresponds to the name of your Thymeleaf template file
    }

    // This method handles requests to like a post
//    @PostMapping("/{postId}/like")
//    @ResponseBody
//    public String likePost(@PathVariable("postId") Long postId) {
//        // Assuming you have a service layer that handles liking a post
//        postService.likePost(postId);
//        return "Liked post with ID: " + postId;
//    }

    // This method handles requests to comment on a post
//    @PostMapping("/{postId}/comment")
//    @ResponseBody
//    public String commentPost(@PathVariable("postId") Long postId, @RequestParam("comment") String comment) {
//        // Assuming you have a service layer that handles commenting on a post
//        postService.commentPost(postId, comment);
//        return "Commented on post with ID: " + postId + ", comment: " + comment;
//    }

    // Assume you have a PostService that handles business logic for posts
    private final PostService postService;
    private final BloggerService bloggerService;



}