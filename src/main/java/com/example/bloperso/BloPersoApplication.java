package com.example.bloperso;

import com.example.bloperso.Entities.*;
import com.example.bloperso.Service.BloggerService;
import com.example.bloperso.dao.BloggerRepository;
import com.example.bloperso.dao.CommentRepository;
import com.example.bloperso.dao.PostRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class BloPersoApplication  {

    public static void main(String[] args) {

      ApplicationContext ctx =  SpringApplication.run(BloPersoApplication.class, args);
      // init(ctx);
    }
public static void init(ApplicationContext ctx){
        BloggerRepository bloggerRepository = ctx.getBean(BloggerRepository.class);
        PostRepository postRepository = ctx.getBean(PostRepository.class);
    CommentRepository commentRepository = ctx.getBean(CommentRepository.class);
    Blogger b1 = new Blogger("username1","fullname","password","012233","aa","email");
    Post post = new Post(b1,"Title","Post  nbr 1",PostCategorie.Politique,"",Visibilite.Public,new ArrayList<>(),new ArrayList<>());
    Comment cmt = new Comment(1L,"Comment 1", Date.valueOf(LocalDate.now()),b1,post);
   bloggerRepository.save(b1);
   postRepository.save(post);
   commentRepository.save(cmt);

}

}
