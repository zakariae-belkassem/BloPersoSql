package com.example.bloperso.dao;

import com.example.bloperso.Entities.Blogger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BloggerRepository extends JpaRepository<Blogger,Long> {

}
