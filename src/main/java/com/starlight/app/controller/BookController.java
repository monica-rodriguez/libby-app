package com.starlight.app.controller;

import com.starlight.app.model.entity.Books;
import com.starlight.app.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000") // Allow requests from the React frontend
@RestController
@RequestMapping("/starlightFolklore") //http://localhost:8080/starlightFolklore
public class BookController {

    private final BookService service;
    public BookController(BookService service) {
        this.service = service;
    }

    /**
     * Search by any combination of firstName, lastName, title, and genre
     * Example search http://localhost:8080/starlightFolklore?lastName=kuang (GET)
     */

    //query search
    @GetMapping
    public ResponseEntity<?> search(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String genre) {

        if (isBlank(firstName) && isBlank(lastName) && isBlank(title) && isBlank(genre)) {
            return ResponseEntity.badRequest().body(
                    "At least one of the search parameters must be provided: author first name, author last name, book title, or book genre"
            );
        }

        List<Books> result = service.search(firstName, lastName, title, genre);
        return ResponseEntity.ok(result);
    }

    //get entire books list
    @GetMapping("/getAll")
    public List<Books> getAll() {
        return  service.getFullList();
    }

    private boolean isBlank(String s) {
        return s == null || s.isBlank();
    }
}
