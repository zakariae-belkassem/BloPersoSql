package com.example.bloperso.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SessionService {


    @Autowired
    public   BloggerService bloggerService;

    @PersistenceContext
    @Autowired
    private  EntityManager entityManager;
    public  String findLoggedInBloggerUsername() {
        return Optional.ofNullable(entityManager
                        .createNativeQuery("SELECT principal_name FROM spring_session")
                        .getSingleResult())
                .map(Object::toString).get();
    }
    public   Long idB(){
        String username = findLoggedInBloggerUsername();
        return bloggerService.getByUsername(username).getId();

    }
}
