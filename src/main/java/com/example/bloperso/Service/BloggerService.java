package com.example.bloperso.Service;

import com.example.bloperso.Entities.Blogger;
import com.example.bloperso.dao.BloggerRepository;
import com.example.bloperso.dao.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BloggerService {
    @Autowired
    private BloggerRepository bloggerRepository;

    public void reg(Blogger b ){
        bloggerRepository.save(b);
    }
}
