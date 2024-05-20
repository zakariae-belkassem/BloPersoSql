package com.example.bloperso.Service;

import com.example.bloperso.Entities.Post;
import com.example.bloperso.dao.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    public List<Post> getAll(){
        return postRepository.findAll();
    }
    public List<Post> getRecentPosts(int howMuch){
        return postRepository.findAll().subList(0,Math.min(postRepository.findAll().size(),howMuch));
    }

    public List<Post> Searched(String keywords) {
        List<Post> l = postRepository.findAll();
        List<Post> result = new ArrayList<>();
        String[] words = keywords.split("\\s+");
        for (String word : words) {
            for (Post p : l) {
                if (p.getCorpsPost().contains(word) || p.getTitle().contains(word) || !result.contains(p))
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
}
