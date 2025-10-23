package com.starlight.app.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "books",
            uniqueConstraints = @UniqueConstraint(name = "ux_books", columnNames = {"FirstName", "LastName", "Title", "Genre"}))
@Data
public class Books {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long bookId;

    @Column(name = "FirstName", nullable = false, length = 100)
    private String firstName;

    @Column(name = "LastName", nullable = false, length = 100)
    private String lastName;

    @Column(name = "Title", nullable = false, length = 100)
    private String bookTitle;

    @Column(name = "Genre", nullable = false, length = 100)
    private String bookGenre;
}
