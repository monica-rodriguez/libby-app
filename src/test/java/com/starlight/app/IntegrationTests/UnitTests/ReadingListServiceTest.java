package com.starlight.app.IntegrationTests.UnitTests;

import com.starlight.app.model.entity.ReadingList;
import com.starlight.app.model.entity.UserListType;
import com.starlight.app.model.entity.Books;
import com.starlight.app.service.BookService;
import com.starlight.app.service.UserListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReadingListServiceTest {

    @Mock
    private BookService bookService;

    private UserListService readingListService;

    @BeforeEach
    void setUp() {
        readingListService = new UserListService(bookService);
    }

    @Test
    void addToList_shouldAddBookToCurrentlyReading() {
        // Arrange
        String userId = "test";
        String bookId = "5";

        Books book = new Books();
        book.setBookId(bookId);
        book.setBookTitle("Test Book");
        book.setFirstName("firstName");
        book.setLastName("lastName");

        when(bookService.getByIndex(bookId)).thenReturn(book);

        // Act
        ReadingList lists = readingListService.addToUserList(userId, bookId, UserListType.CURRENT);

        // Assert
        verify(bookService).getByIndex(bookId);
        assertNotNull(lists);
        assertEquals(1, lists.getCurrentlyReading().size());
        assertEquals("Test Book", lists.getCurrentlyReading().get(0).getBookTitle());
        assertTrue(lists.getFinished().isEmpty());
        assertTrue(lists.getTbr().isEmpty());
    }

    @Test
    void addToList_shouldAddBookToFinished() {
        // Arrange
        String userId = "testUser";
        String bookId = "7";

        Books book = new Books();
        book.setBookId(bookId);
        book.setBookTitle("Finished Book");

        when(bookService.getByIndex(bookId)).thenReturn(book);

        // Act
        ReadingList lists = readingListService.addToUserList(userId, bookId, UserListType.FINISHED);

        // Assert
        assertEquals(1, lists.getFinished().size());
        assertEquals("Finished Book", lists.getFinished().get(0).getBookTitle());
        assertTrue(lists.getCurrentlyReading().isEmpty());
        assertTrue(lists.getTbr().isEmpty());
    }

    @Test
    void getListsForUser_shouldReturnSameInstanceForSameUser() {
        // Arrange
        String userId = "testUser";

        // Act
        ReadingList first = readingListService.getUserLists(userId);
        ReadingList second = readingListService.getUserLists(userId);

        // Assert
        assertSame(first, second, "Expected same UserReadingLists instance for same userId");
    }
}
