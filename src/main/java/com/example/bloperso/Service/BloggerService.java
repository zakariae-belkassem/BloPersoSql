package com.example.bloperso.Service;

import com.example.bloperso.Entities.Blogger;
import com.example.bloperso.Entities.Comment;
import com.example.bloperso.Entities.Post;
import com.example.bloperso.dao.BloggerRepository;
import com.example.bloperso.dao.CommentRepository;
import com.example.bloperso.dao.PostRepository;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BloggerService {
    @Autowired
    private BloggerRepository bloggerRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;

    public void reg(Blogger b ){
        bloggerRepository.save(b);
    }

    public void comment(Comment c,Long idB,Long idP){
        c.setBlogger(bloggerRepository.findById(idB).orElse(null));

        Optional<Post> pp =  postRepository.findById(idP);
        if (!pp.isPresent()) {
            return ;
        }
        Post p = pp.get();
        c.setCreated(Date.valueOf(LocalDate.now()));
        p.addComment(c);
        commentRepository.save(c);
        postRepository.save(p);

    }
    public void like(long idP,long idB){
        Post p = postRepository.findById(idP).orElse(null);

    }
}
