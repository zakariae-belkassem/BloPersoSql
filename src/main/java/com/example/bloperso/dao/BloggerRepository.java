package com.example.bloperso.dao;

import com.example.bloperso.Entities.Blogger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BloggerRepository extends JpaRepository<Blogger,Long> {



    Blogger findByEmail(String email);

    Blogger findBloggerByUsername(String username);
}
