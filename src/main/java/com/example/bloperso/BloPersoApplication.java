package com.example.bloperso;

import com.example.bloperso.Entities.*;
import com.example.bloperso.Service.BloggerService;
import com.example.bloperso.dao.BloggerRepository;
import com.example.bloperso.dao.PostRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class BloPersoApplication {

    public static void main(String[] args) {

        ApplicationContext ctx = SpringApplication.run(BloPersoApplication.class, args);
       /* Blogger b = new Blogger("u1","omar hafidi","pass","8768976897","dzqdqz","email@gmail.com");
        BloggerRepository bloggerRepository = ctx.getBean(BloggerRepository.class);




       // bloggerRepository.save(b);
        BloggerService bloggerService = new BloggerService();
        PostRepository postRepository = ctx.getBean(PostRepository.class);
       bloggerService.comment(new Comment(),1L,1L);
         //   bloggerService.like(1L,1L);
       // postRepository.deleteAll();
        //bloggerRepository.deleteAll();
        //bloggerRepository.save(b);
        //postRepository.save(new Post(b,"neww title","thiss is a new postdzqdqz dqzdqzdqzd qzdqzdqzdqz dqzdqzdqzd qzdqzdqz dqzdqzdqzd qzdqz",PostCategorie.Actualite,"../static/images/img.jpg",Visibilite.Public,null,null));
*/
    }

}
