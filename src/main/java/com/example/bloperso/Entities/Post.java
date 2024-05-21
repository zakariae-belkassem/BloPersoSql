package com.example.bloperso.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;

@Data @Entity @AllArgsConstructor @NoArgsConstructor
public class Post {
    @Transient
    private int nbrComment ;
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "blogger_id")
    private Blogger blogger;
    @Column(name="title",nullable=false)
    private String title;
    @Column(name="content_Post",nullable=false,columnDefinition = "LONGTEXT")
    private String CorpsPost;
    @Column(name="theme",nullable=false)
    @Enumerated(EnumType.STRING)
    private PostCategorie theme;


    @Column(name="visibilite",nullable = false)
    private Visibilite visibilite ;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Blogger> likers;
    @Column(name = "image",columnDefinition = "LONGTEXT")

    private String img;

    public Post(Blogger blogger, String title, String corpsPost, PostCategorie theme, Visibilite visibilite, List<Comment> comments, List<Blogger> likers) {
        this.blogger = blogger;
        this.title = title;
        CorpsPost = corpsPost;
        this.theme = theme;

        this.visibilite = visibilite;
        this.comments = comments;
        this.likers = likers;
        nbrComment = 0;
    }

    public void addLiker(Blogger b){
        likers.add(b);
    }
    public void addComment(Comment c){comments.add(c);}
    public void removeLiker(Blogger b){

        likers.remove(b);
    }
    public void setImg(Blob blob) {
        byte[] bytes = new byte[0];
        try {
            bytes = blob.getBytes(1, (int) blob.length());
            img = Base64.getEncoder().encodeToString(bytes);
        } catch (SQLException e) {
            //
        }


    }

}
