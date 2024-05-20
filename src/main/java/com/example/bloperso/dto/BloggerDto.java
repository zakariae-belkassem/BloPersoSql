package com.example.bloperso.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BloggerDto {
    private Long id;
    private String userName;
    private String fullName;
    private String phoneNumber;
    private String password;

    private String adresse;
    private List<BloggerDto> friends ;
    private String email;

}