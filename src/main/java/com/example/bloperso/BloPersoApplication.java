package com.example.bloperso;

import com.example.bloperso.Entities.Blogger;
import com.example.bloperso.dao.BloggerRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class BloPersoApplication {

    public static void main(String[] args) {

        ApplicationContext ctx = SpringApplication.run(BloPersoApplication.class, args);
        BloggerRepository bloggerRepository = ctx.getBean(BloggerRepository.class);
        bloggerRepository.save(new Blogger(null,"omar","omar","pass","98327493284",0,null));
    }

}
