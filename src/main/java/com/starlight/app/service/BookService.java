package com.starlight.app.service;

import com.starlight.app.model.entity.Books;
import com.starlight.app.repository.CsvLoader;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Component
public class BookService {

    private static final Logger log = LoggerFactory.getLogger(BookService.class);

    private volatile List<Books> list = Collections.emptyList();

    @PostConstruct
    public void loadBooksList() {
        log.info("Loading book data...");
        List<Books> returnedList = CsvLoader.booksRowMapper(Books.class, "books.csv");

        for (int i = 0; i < returnedList.size(); i++ ) {
            returnedList.get(i).setBookId(String.valueOf(i));
        }
        this.list = Collections.unmodifiableList(returnedList);
        log.info("Loaded {} books", list.size());
    }

    public List<Books> search(String firstName, String lastName, String title, String genre) {
        return list.stream()
                .filter(books -> matchFirstName(books, firstName))
                .filter(books -> matchLastName(books, lastName))
                .filter(books -> containsIgnoreCase(books.getBookTitle(), title))
                .filter(books -> containsIgnoreCase(books.getBookGenre(), genre))
                .toList();
    }

    private boolean matchFirstName(Books books, String query) {
        if (isBlank(query)) {
            return true;
        }
        String q = norm(query);

        String fn = books.getFirstName();
        if (!isBlank(fn)) {
            for (String returnedFn : fn.split("/")) {
                if (!isBlank(returnedFn) && norm(returnedFn).contains(q)) {
                    return true;
                }
            }
        }

        return books.getAuthors().stream()
                .filter(Objects::nonNull)
                .map(this::norm)
                .anyMatch(a -> a.contains(q));
    }

    private boolean matchLastName(Books books, String query) {
        if (isBlank(query)) {
            return true;
        }
        String q = norm(query);

        String ln = books.getLastName();
        if (!isBlank(ln)) {
            for (String returnedLn : ln.split("/")) {
                if (!isBlank(returnedLn) && norm(returnedLn).contains(q)) {
                    return true;
                }
            }
        }

        return books.getAuthors().stream()
                .filter(Objects::nonNull)
                .map(this::norm)
                .anyMatch(a -> a.contains(q));
    }

    private boolean containsIgnoreCase(String value, String query) {
        if (isBlank(query)) {
            return true;
        }
        if (value == null) {
            return false;
        }
        return norm(value).contains(norm(query));
    }

    private boolean isBlank(String s) {
        return s == null || s.isBlank();
    }

    private String norm(String s) {
        return s == null ? "" : s.toLowerCase(Locale.ROOT).trim();
    }

    public List<Books> getFullList() {
        return list;
    }

    public Books getByIndex(String bookId) {
        return list.stream()
                .filter(b -> bookId != null && bookId.equals(b.getBookId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Book not found: " + bookId));
    }
}
