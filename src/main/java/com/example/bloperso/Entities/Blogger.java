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
    private String Username;

    @Column(name="full_name",length=100,nullable=false)
    private String FullName;

    @Column(name="password",length=100,nullable=false)
    private String Password;

    @Column(name="phone_number",length=100)
    private String Phone_number;

    @Transient
    private int nbrPosts = 0;

    @OneToMany
    private List<Blogger> friends;

    @Column(name = "Adresse")
    private String Adresse ;
    @Column(name = "email")
    private String email;
    @OneToMany(mappedBy = "blogger")
    private List<Comment> comment ;

    @ManyToOne
    private Blogger blogger ;
    public Blogger(String username, String fullName, String password, String phone_number,  String adresse, String email) {
        Username = username;
        FullName = fullName;
        Password = password;
        Phone_number = phone_number;
        Adresse = adresse;
        this.email = email;
    }
}