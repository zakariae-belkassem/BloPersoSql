package com.example.bloperso.web;

import com.example.bloperso.Entities.Blogger;
import com.example.bloperso.Entities.Post;
import com.example.bloperso.Entities.PostCategorie;
import com.example.bloperso.Entities.Visibilite;
import com.example.bloperso.Service.BloggerService;
import com.example.bloperso.Service.PostService;
import com.example.bloperso.dao.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Controller
public class PostController {
    @Autowired
    BloggerService bloggerService;
    @Autowired
    private PostRepository postRepository ;
    @Autowired
    private PostService postService;
    @Value("${upload.path}")
    private String uploadPath;

    private Long idBlogger = 1L;
    @RequestMapping(value = "")
    public String index(Model model){
        List<Post> posts = postRepository.findAll();
        model.addAttribute("poste",posts);
            model.addAttribute("image","images/img.jpg");
        return "indexx";
    }
//    @GetMapping (value = "/post/{pId}")
//    public String SpePost(Model model, @RequestParam(name = "pId",defaultValue = "0") Long id){
//        Post post = postRepository.findById(id).orElse(null);
//        model.addAttribute("post",post);
//        return "post";
//    }
//@GetMapping( "/add")
//public String showAddPost(Model model){
//    model.addAttribute("newPost",new Post());
//    model.addAttribute("categories",PostCategorie.values());
//    return "addPost" ;
//}
//    @RequestMapping(value = "/add", method = RequestMethod.POST)
//    public String addPost(@ModelAttribute("newPost") Post newPost){
//        System.out.println("l bdo ");
//        Blogger b = new Blogger("u1","omar hafidi","pass","8768976897","dzqdqz","email@gmail.com");
//        bloggerService.reg(b);
//        newPost.setBlogger(b);
//        postService.createPost(newPost);
//        return "redirect:/indexx";
//    }
    // Method to show the add post form
    @GetMapping("/add")
    public String showAddPostForm(Model model) {

        model.addAttribute("categories", PostCategorie.values());
        model.addAttribute("visibilite",Visibilite.values());
        return "addPost";
    }

    // Method to handle form submission and save post data
    @PostMapping("/add")
    public String addPost(@ModelAttribute("newPost") Post newPost, @RequestParam("visibilite") Visibilite visibilite, @RequestParam("image")MultipartFile file) {



        // Set the Blogger for the new post
        System.out.println("-------------------");
        Blogger b = new Blogger("u1","omar hafidi","pass","8768976897","dzqdqz","email@gmail.com");
        bloggerService.reg(b);
        newPost.setBlogger(b);

        // Set the visibilite for the new post
       // newPost.setVisibilite(visibilite);
        try {
            Path path = Paths.get(uploadPath);

            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            Files.copy(Path.of(fileName),Path.of("/images/"+b.getFullName()),StandardCopyOption.REPLACE_EXISTING);




        } catch (IOException e) {
            e.printStackTrace();
        }

        newPost.setImage("/images/"+b.getFullName());
        // Save the post to the database
        postRepository.save(newPost);

        // Redirect to a confirmation page or any other appropriate page
        return "redirect:/"; // Redirect back to the home page
    }
    @PostMapping(value = "/like")
    public String LikeP(@RequestParam(name = "id")Long id){

        bloggerService.like(id,idBlogger);
        return "redirect:/post/"+id;
    }
//popular posts + if not connected show preview only -> on click loginPage + Sessions + Security

    @PostMapping(value = "/deleteP")
    public String removeP(@RequestParam(name="id")Long id){
        bloggerService.removePost(id);
        return "indexx";
    }


}


