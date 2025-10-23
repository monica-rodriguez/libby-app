package com.starlight.app.repository;

import com.starlight.app.model.entity.Books;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BooksRepo extends JpaRepository<Books, Long> {
    //find by: full matches
    List<Books> findByFirstNameIgnoreCase(String firstName);
    List<Books> findByLastNameIgnoreCase(String lastName);
    List<Books> findByBookTitleIgnoreCase(String bookTitle);
    List<Books> findByBookGenreIgnoreCase(String bookGenre);

    //find by: partial matches
    List<Books> findByFirstNameContainingIgnoreCase(String firstName);
    List<Books> findByLastNameContainingIgnoreCase(String lastName);
    List<Books> findByBookTitleContainingIgnoreCase(String bookTitle);

}
