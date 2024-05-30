package com.example.bloperso.Entities;

import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Data
@NoArgsConstructor@Table(name = "Comment")@AllArgsConstructor@DynamicUpdate@DynamicInsert
public class Comment implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "Content",nullable = false)
    private String Content;
    @Column(name = "Date_de_creation",nullable = false)
    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    private Date Created;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "blogger_id")
    private Blogger blogger;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    private  Post post;

}
