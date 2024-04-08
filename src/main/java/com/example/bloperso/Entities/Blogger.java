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
    @Column(name="phone_number",length=100,nullable=true)
    private String Phone_number;
    @Transient
    private int nbrPosts = 0;
    @OneToMany
    private List<Blogger> friends;

}
