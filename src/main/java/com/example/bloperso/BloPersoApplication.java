package com.example.bloperso;

import com.example.bloperso.Entities.*;
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
        BloggerRepository bloggerRepository = ctx.getBean(BloggerRepository.class);

        PostRepository postRepository = ctx.getBean(PostRepository.class);


    }

}
