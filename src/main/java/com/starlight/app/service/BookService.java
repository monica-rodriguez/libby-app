package com.starlight.app.service;

import com.starlight.app.repository.BooksRepo;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    private final BooksRepo repository;
    public BookService(BooksRepo repository) { this.repository = repository; }
}
