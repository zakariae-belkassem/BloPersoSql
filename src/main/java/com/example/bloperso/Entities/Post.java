package com.example.bloperso.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.type.descriptor.java.StringJavaType;

import java.util.List;

@Data @Entity @AllArgsConstructor @NoArgsConstructor
public class Post {
    private int nbrComment = 0;
    private static Long compteur=1L;
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Blogger blogger;
    @Column(name="title",nullable=false)
    private String title;
    @Column(name="content_Post",nullable=false)
    private String CorpsPost;
    @Column(name="theme",nullable=false)
    private PostCategorie theme;
    @Column(name="image",nullable=true)
    private String IconUrl;
    @Column(name="privacy_type",nullable=false)
    private Visibilite visibilite = Visibilite.Private;
    @Column(name = "Adresse",nullable = false)
    private String Adresse ;
    @Column(name = "email")
    private String email;
    @OneToMany
    private List<Comment> Comments;
    @OneToMany
    private List<Blogger> Likers;
    
}
