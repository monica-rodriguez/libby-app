package com.starlight.app.IntegrationTests.UnitTests;

import com.starlight.app.model.entity.Books;
import com.starlight.app.repository.CsvLoader;
import com.starlight.app.service.BookService;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Requires Mockito inline for static mocking:
 *
 * <dependency>
 *   <groupId>org.mockito</groupId>
 *   <artifactId>mockito-inline</artifactId>
 *   <version>5.2.0</version>
 *   <scope>test</scope>
 * </dependency>
 */
class BookServiceTest {

    @Test
    void loadBooksList() {
        List<Books> seed = List.of(
                book("Rebecca", "Kuang", "Babel", "Fantasy"),
                book("Neil", "Gaiman", "Coraline", "Horror")
        );

        try (MockedStatic<CsvLoader> mocked = mockStatic(CsvLoader.class)) {
            mocked.when(() -> CsvLoader.booksRowMapper(Books.class, "books.csv"))
                    .thenReturn(seed);

            BookService svc = new BookService();

            svc.loadBooksList();

            List<Books> list = svc.getFullList();
            assertEquals(2, list.size(), "Service should hold CSV-loaded books");
            assertThrows(UnsupportedOperationException.class, () -> list.add(new Books()),
                    "Returned list must be unmodifiable");
        }
    }

    @Test
    void search_matchesFirstName() {
        // Lee should match Lee Child Better Off Dead
        Books b1 = book("Lee/Andrew", "Child", "Better Off Dead", "Mystery");
        // control
        Books b2 = book("Haruki", "Murakami", "Kafka on the Shore", "Literary");

        runSearch(seed(b1, b2), svc -> {
            assertEquals(1, svc.search("lee", null, null, null).size());
            assertEquals(1, svc.search("haru", null, null, null).size());
        });
    }

    @Test
    void search_matchesLastName_withSlashSeparatedValues() {
        Books b1 = book("James/Marshall", "Patterson/Karp", "Kill Me If You Can", "Thriller");
        Books b2 = book("Victoria", "Aveyard", "Red Queen", "Fantasy");

        runSearch(seed(b1, b2), svc -> {
            assertEquals(1, svc.search(null, "patt", null, null).size());
            assertEquals(1, svc.search(null, "ave", null, null).size());
        });
    }

    @Test
    void search_title_and_genre() {
        Books b1 = book("Rebecca", "Kuang", "Babel", "Fantasy");
        Books b2 = book("Neil", "Gaiman", "Coraline", "Horror");

        runSearch(seed(b1, b2), svc -> {
            // case-insensitive title
            assertEquals(1, svc.search(null, null, "babel", null).size());
            // case-insensitive genre
            assertEquals(1, svc.search(null, null, null, "horror").size());
            // blank params => no filter => full list
            assertEquals(2, svc.search("", "   ", null, null).size());
        });
    }
        @Test
void search_firstName_and_lastName() {
    Books b1 = book("James", "Patterson", "Invisible", "Thriller");
    Books b2 = book("James", "Cameron", "Avatar", "Science Fiction");

    runSearch(seed(b1, b2), svc -> {
        var result = svc.search("James", "Patterson", null, null);
        assertEquals(1, result.size());
        assertEquals("Invisible", result.get(0).getBookTitle());
    });
}

@Test
void search_firstName_and_genre() {
    Books b1 = book("James", "Patterson", "Invisible", "Thriller");
    Books b2 = book("James", "Patterson", "The Gift", "Fantasy");

    runSearch(seed(b1, b2), svc -> {
        var result = svc.search("James", null, null, "Thriller");
        assertEquals(1, result.size());
        assertEquals("Invisible", result.get(0).getBookTitle());
    });
}

@Test
void search_firstName_and_title() {
    Books b1 = book("James", "Patterson", "Invisible", "Thriller");
    Books b2 = book("James", "Patterson", "The Gift", "Fantasy");

    runSearch(seed(b1, b2), svc -> {
        var result = svc.search("James", null, "Invisible", null);
        assertEquals(1, result.size());
        assertEquals("Thriller", result.get(0).getBookGenre());
    });
}

@Test
void search_lastName_and_genre() {
    Books b1 = book("James", "Patterson", "Invisible", "Thriller");
    Books b2 = book("Victoria", "Aveyard", "Red Queen", "Fantasy");

    runSearch(seed(b1, b2), svc -> {
        var result = svc.search(null, "Patterson", null, "Thriller");
        assertEquals(1, result.size());
        assertEquals("Invisible", result.get(0).getBookTitle());
    });
}

@Test
void search_lastName_and_title() {
    Books b1 = book("James", "Patterson", "Invisible", "Thriller");
    Books b2 = book("Victoria", "Aveyard", "Red Queen", "Fantasy");

    runSearch(seed(b1, b2), svc -> {
        var result = svc.search(null, "Patterson", "Invisible", null);
        assertEquals(1, result.size());
        assertEquals("Thriller", result.get(0).getBookGenre());
    });
}

@Test
void search_title_and_genre_combination() {
    Books b1 = book("Rebecca", "Kuang", "Babel", "Fantasy");
    Books b2 = book("Neil", "Gaiman", "Coraline", "Horror");

    runSearch(seed(b1, b2), svc -> {
        var result = svc.search(null, null, "Babel", "Fantasy");
        assertEquals(1, result.size());
        assertEquals("Rebecca", result.get(0).getFirstName());
    });
}

@Test
void search_multiple_results_for_author() {
    Books b1 = book("James", "Patterson", "Invisible", "Thriller");
    Books b2 = book("James", "Patterson", "Criss Cross", "Thriller");
    Books b3 = book("James", "Patterson", "The Black Book", "Thriller");

    runSearch(seed(b1, b2, b3), svc -> {
        var result = svc.search("James", "Patterson", null, "Thriller");
        assertEquals(3, result.size(), "Should return all thrillers by James Patterson");
    });
}

@Test
void search_allNullParameters_throwsException() {
    Books b1 = book("James", "Patterson", "Invisible", "Thriller");

    runSearch(seed(b1), svc -> {
        assertThrows(IllegalArgumentException.class, () ->
                svc.search(null, null, null, null),
                "Expected exception when all parameters are null");
    });
    }

    private interface ServiceConsumer { void accept(BookService svc); }

    private static void runSearch(List<Books> seed, ServiceConsumer consumer) {
        try (MockedStatic<CsvLoader> mocked = mockStatic(CsvLoader.class)) {
            mocked.when(() -> CsvLoader.booksRowMapper(Books.class, "books.csv"))
                    .thenReturn(seed);
            BookService svc = new BookService();
            svc.loadBooksList();
            consumer.accept(svc);
        }
        }
    

    private static List<Books> seed(Books... books) {
        return List.of(books);
    }

    private static Books book(String first, String last, String title, String genre) {
        Books b = new Books();
        b.setFirstName(first);
        b.setLastName(last);
        b.setBookTitle(title);
        b.setBookGenre(genre);
        return b;
    }
}
