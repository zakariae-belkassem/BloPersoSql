package com.example.bloperso.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data@NoArgsConstructor@AllArgsConstructor@Entity@Table(name = "Blogger")
public class Blogger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long Id ;

    @Column(name="User_name",length=100,nullable=false)
    private String userName;

    @Column(name="full_name",length=100,nullable=false)
    private String fullName;

    @Column(name="password",length=100,nullable=false)
    private String password;

    @Column(name="phone_number",length=100)
    private String phoneNumber;

    @Transient
    private int nbrPosts = 0;

    @OneToMany
    private List<Blogger> friends;

    @Column(name = "Adresse")
    private String adresse ;
    @Column(name = "email")
    private String email;
    @OneToMany(mappedBy = "blogger")
    private List<Comment> comment ;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Post> bookMarks ;
    @OneToMany
    private List<Blogger> friendRequest;

    @OneToMany(mappedBy = "blogger")
    private List<Post> posts;
    @ManyToMany
    private List<Post> likedPosts;

    public Blogger(String un, String fn, String pass, String phone_number,  String a, String email) {
        userName = un;
        fullName = fn;
        password = pass;
        phoneNumber = phone_number;
        adresse = a;
        this.email = email;
    }
    public void AddBookMark(Post p ){
        bookMarks.add(p);
    }
    public void removeBookMark(Post p){
        bookMarks.remove(p);
    }
    public void AddRequest(Blogger blogger){
        friendRequest.add(blogger);
    }
    public void RemoveRequest(Blogger b ){
        friendRequest.remove(b);
    }

    public void addLikedpost(Post p){
        likedPosts.add(p);
    }
    public void removeLikedpost(Post p){
        likedPosts.remove(p);
    }

}