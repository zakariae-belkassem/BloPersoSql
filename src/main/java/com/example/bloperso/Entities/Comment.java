package com.example.bloperso.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity@Data@NoArgsConstructor@Table(name = "Comment")@AllArgsConstructor
public class Comment {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "Content",nullable = false)
    private String Content;
    @Column(name = "Date_de_creation",nullable = false)
    private Date Created;
    @OneToOne
    private Blogger blogger;

}
