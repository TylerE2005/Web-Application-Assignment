package com.humber.Week8_Assignment_3.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ItemUser {
    @Id //Marks ID Field as primary key for user object
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    //The Fields belows cannot be empty/null in the database

    //Must be a unique username
    @Column(nullable = false, unique = true)
    private String username;

    //The maximum password length is 64
    @Column(nullable = false, length = 64)
    private String password;

    @Column(nullable = false)
    private String role;
}
