package com.example.bloperso.Entities;

import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Date;

@Entity@Data@NoArgsConstructor@Table(name = "Comment")@AllArgsConstructor@DynamicUpdate@DynamicInsert
public class Comment {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "Content",nullable = false)
    private String Content;
    @Column(name = "Date_de_creation",nullable = false)
    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    private Date Created;
    @ManyToOne
    private Blogger blogger;

}
