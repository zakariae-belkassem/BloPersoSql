package com.example.bloperso.Service;

import com.example.bloperso.Entities.Post;
import com.example.bloperso.Entities.PostCategorie;
import com.example.bloperso.Entities.Visibilite;
import com.example.bloperso.dao.BloggerRepository;
import com.example.bloperso.dao.PostRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private BloggerRepository bloggerRepository;


    public List<Post> getAll(){
        return postRepository.findAll();
    }


    public List<Post> Searched(String keywords) {
        List<Post> l = postRepository.findAll().stream().filter(e->e.getVisibilite().equals(Visibilite.Public)).toList();
        List<Post> result = new ArrayList<>();
        String[] words = keywords.split("\\s+");
        for (String word : words) {
            for (Post p : l) {
                if (p.getCorpsPost().contains(word) || p.getTitle().contains(word) || !result.contains(p)||p.getBlogger().getUsername().contains(word))
                    result.add(p);
            }
        }
        return result;
    }
    public Post getPostById(Long id){
        return postRepository.findById(id).orElse(null);
    }
    public void createPost(Post p ){
        postRepository.save(p);
    }

    public Post Featured(){
        return postRepository.findById(1L).orElse(null);
    }
    public List<Post> FilterByTopic(String topic){
        return postRepository.findAll().stream().filter(e-> e.getTheme().equals(PostCategorie.valueOf(topic)) && e.getVisibilite().equals(Visibilite.Public)).toList();
    }


    public void modifyPost(Post p, MultipartFile file, Long idBlogger) {

        if (!file.isEmpty()) {
            Blob blob ;
            try {
                blob = new SerialBlob(file.getBytes());
                p.setImg(blob);
            } catch (SQLException | IOException e) {
               //
            }

        }
        p.setBlogger(bloggerRepository.findById(idBlogger).orElse(null));
        postRepository.save(p);
    }

    public List<Post> publicPosts() {
        return postRepository.findAll().stream().filter(e->e.getVisibilite().equals(Visibilite.Public)).toList();
    }
}
