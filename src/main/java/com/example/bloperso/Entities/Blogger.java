package com.example.bloperso.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Data@NoArgsConstructor@AllArgsConstructor@Entity@Table(name = "Blogger")
public class Blogger implements UserDetails, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long Id ;

    @Column(name="User_name",length=100,nullable=false,unique = true)
    private String username;

    @Column(name="full_name",length=100,nullable=false)
    private String fullName;

    @Column(name="password",length=100,nullable=false)
    private String password;

    @Column(name="phone_number",length=100)
    private String phoneNumber;

    @Transient
    private int nbrPosts = 0;

    @OneToMany(fetch = FetchType.EAGER)
    private List<Blogger> friends;

    @Column(name = "Adresse")
    private String adresse ;
    @Column(name = "email")
    private String email;
    @OneToMany(mappedBy = "blogger",fetch = FetchType.EAGER)
    private List<Comment> comment ;
    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Post> bookMarks ;
    @OneToMany(fetch = FetchType.EAGER)
    private List<Blogger> friendRequest;

    @OneToMany(mappedBy = "blogger",fetch = FetchType.EAGER)
    private List<Post> posts;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Post> likedPosts;

    public Blogger(String un, String fn, String pass, String phone_number,  String a, String email) {
        username = un;
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "Blogger{" +
                "Id=" + Id +
                ", username='" + username + '\'' +
                ", fullName='" + fullName + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", nbrPosts=" + nbrPosts +
                ", adresse='" + adresse + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}