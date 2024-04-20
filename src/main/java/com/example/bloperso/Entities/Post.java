package com.example.bloperso.Entities;

import com.example.bloperso.dao.CommentRepository;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.type.descriptor.java.StringJavaType;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data @Entity @AllArgsConstructor @NoArgsConstructor
public class Post {
    @Transient
    private int nbrComment ;
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Blogger blogger;
    @Column(name="title",nullable=false)
    private String title;
    @Column(name="content_Post",nullable=false)
    private String CorpsPost;
    @Column(name="theme",nullable=false)
    private PostCategorie theme;
    @Column(name = "image")
    private String image;
    @Column(name="visibilite",nullable = false)
    private Visibilite visibilite ;

    @OneToMany(mappedBy = "post")
    private List<Comment> Comments;

    @OneToMany
    private List<Blogger> Likers;

    public Post(Blogger blogger, String title, String corpsPost, PostCategorie theme, String image, Visibilite visibilite, List<Comment> comments, List<Blogger> likers) {
        this.blogger = blogger;
        this.title = title;
        CorpsPost = corpsPost;
        this.theme = theme;
        this.image = image;
        this.visibilite = visibilite;
        Comments = comments;
        Likers = likers;
        nbrComment = 0;
    }

    public void addLiker(Blogger b){
        Likers.add(b);
    }
    public void addComment(Comment c){Comments.add(c);}
}
