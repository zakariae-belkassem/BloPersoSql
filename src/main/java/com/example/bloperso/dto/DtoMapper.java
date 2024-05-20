package com.example.bloperso.dto;

import com.example.bloperso.Entities.Blogger;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DtoMapper {
    public BloggerDto toDto(Blogger blogger){
        if (blogger ==null) return null ;
        BloggerDto bloggerDto = new BloggerDto();
        bloggerDto.setId(blogger.getId());
        bloggerDto.setUserName(blogger.getUserName());
        bloggerDto.setFullName(blogger.getFullName());
        bloggerDto.setPhoneNumber(blogger.getPhoneNumber());
        bloggerDto.setAdresse(blogger.getAdresse());
        bloggerDto.setEmail(blogger.getEmail());
        List<BloggerDto> b = new ArrayList<>();
        blogger.getFriends().forEach(f->b.add(toDto(f)));
        bloggerDto.setFriends(b);
         return bloggerDto;
    }

    public Blogger toEntity(BloggerDto bloggerDto) {
        if (bloggerDto == null) {
            return null;
        }

        Blogger blogger = new Blogger();
        blogger.setId(bloggerDto.getId());
        blogger.setUserName(bloggerDto.getUserName());
        blogger.setFullName(bloggerDto.getFullName());
        blogger.setPhoneNumber(bloggerDto.getPhoneNumber());
        blogger.setAdresse(bloggerDto.getAdresse());
        blogger.setEmail(bloggerDto.getEmail());
        blogger.setPassword(bloggerDto.getPassword());
        return blogger;
    }
}
