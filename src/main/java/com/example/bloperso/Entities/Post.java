package com.example.bloperso.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.type.descriptor.java.StringJavaType;

import java.util.List;

@Data @Entity @AllArgsConstructor @NoArgsConstructor
public class Post {
    @Transient
    private int nbrComment ;
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
    @Column(name="image")
    private String IconUrl;
    @Column(name="privacy_type",nullable=false)
    private Visibilite visibilite = Visibilite.Private;
    @OneToMany
    private List<Comment> Comments;
    @OneToMany
    private List<Blogger> Likers;

    public Post(Blogger blogger, String title, String corpsPost, PostCategorie theme, String iconUrl, Visibilite visibilite, List<Comment> comments, List<Blogger> likers) {
        this.blogger = blogger;
        this.title = title;
        CorpsPost = corpsPost;
        this.theme = theme;
        IconUrl = iconUrl;
        this.visibilite = visibilite;
        Comments = comments;
        Likers = likers;
        nbrComment = 0;
    }
}
