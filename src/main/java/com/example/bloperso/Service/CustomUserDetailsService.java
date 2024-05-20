package com.example.bloperso.Service;

import com.example.bloperso.Entities.Blogger;
import com.example.bloperso.dao.BloggerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private BloggerRepository bloggerRepository;

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Blogger blogger = bloggerRepository.findByEmail(email);
        if (blogger == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new User(blogger.getEmail(), blogger.getPassword(), Collections.emptyList());
    }
}