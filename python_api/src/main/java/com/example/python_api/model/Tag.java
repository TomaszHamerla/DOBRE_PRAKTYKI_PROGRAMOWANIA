package com.example.python_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table
@Entity
public class Tag {
    @Id
   // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int movieId;
    private int userId;
    private String tag;
    private long timestamp;
}
