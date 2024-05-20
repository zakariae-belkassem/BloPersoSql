package com.example.bloperso.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BloggerDto {
    private Long id;
    private String username;
    private String fullName;
    private String phoneNumber;
    private String password;

    private String adresse;
    private String email;

}