package com.starlight.app.IntegrationTests.UnitTests;

import com.starlight.app.model.entity.UserListType;
import com.starlight.app.service.StreakService;
import com.starlight.app.service.UserListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StreaksServiceTest {
    @Mock
    private UserListService userListService;

    @InjectMocks
    private StreakService streakService;

    private final String userId = "testUser";
    private final String bookId = "5";

    @BeforeEach
    void setUp() {
    }

    @Test
    void logReading_shouldThrowIfBookNotInCurrentList() {
        LocalDate today = LocalDate.now();
        when(userListService.isBookInList(userId, bookId, UserListType.CURRENT))
                .thenReturn(false);

        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> streakService.logReading(userId, bookId, today)
        );

        assertEquals("Book must be in CURRENT list to log streak.", ex.getMessage());

        verify(userListService).isBookInList(userId, bookId, UserListType.CURRENT);
    }

    @Test
    void logReading_shouldCreateSingleDayStreak() {
        LocalDate today = LocalDate.now();
        when(userListService.isBookInList(userId, bookId, UserListType.CURRENT))
                .thenReturn(true);

        streakService.logReading(userId, bookId, today);
        int streak = streakService.getCurrentStreak(userId);

        assertEquals(1, streak);
        verify(userListService).isBookInList(userId, bookId, UserListType.CURRENT);
    }

    @Test
    void getCurrentStreak_shouldCountConsecutiveDays() {
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);
        LocalDate twoDaysAgo = today.minusDays(2);
        String otherBookId = "10";

        when(userListService.isBookInList(userId, bookId, UserListType.CURRENT))
                .thenReturn(true);
        when(userListService.isBookInList(userId, otherBookId, UserListType.CURRENT))
                .thenReturn(true);

        streakService.logReading(userId, bookId, twoDaysAgo);
        streakService.logReading(userId, otherBookId, yesterday);
        streakService.logReading(userId, bookId, today);

        int streak = streakService.getCurrentStreak(userId);

        assertEquals(3, streak);
    }

    @Test
    void getCurrentStreak_shouldBreakIfThereIsAGap() {
        LocalDate today = LocalDate.now();
        LocalDate twoDaysAgo = today.minusDays(2); // gap yesterday
        when(userListService.isBookInList(userId, bookId, UserListType.CURRENT))
                .thenReturn(true);

        streakService.logReading(userId, bookId, twoDaysAgo);

        int streak = streakService.getCurrentStreak(userId);

        assertEquals(0, streak, "Gap between last read day and today should give streak 0");
    }

    @Test
    void logReading_shouldReplaceEntryForSameDate() {
        LocalDate today = LocalDate.now();
        String firstBook = "A";
        String secondBook = "B";

        when(userListService.isBookInList(userId, firstBook, UserListType.CURRENT)).thenReturn(true);
        when(userListService.isBookInList(userId, secondBook, UserListType.CURRENT)).thenReturn(true);

        streakService.logReading(userId, firstBook, today);
        streakService.logReading(userId, secondBook, today);

        Map<LocalDate, String> calendar = streakService.getMonthlyCalendar(
                userId, today.getYear(), today.getMonthValue()
        );

        assertEquals(1, calendar.size(), "There should only be one entry for that date");
        assertEquals(secondBook, calendar.get(today),
                "The last logged book for that date should win");
    }

    @Test
    void getMonthlyCalendar_shouldReturnOnlyMonthEntries() {
        LocalDate targetDate = LocalDate.of(2025, 1, 15);
        LocalDate previousMonth = targetDate.minusMonths(1);
        LocalDate nextMonth = targetDate.plusMonths(1);

        when(userListService.isBookInList(userId, bookId, UserListType.CURRENT))
                .thenReturn(true);

        streakService.logReading(userId, bookId, previousMonth);
        streakService.logReading(userId, bookId, targetDate);
        streakService.logReading(userId, bookId, nextMonth);

        Map<LocalDate, String> calendar = streakService.getMonthlyCalendar(
                userId, 2025, 1
        );

        assertEquals(1, calendar.size());
        assertTrue(calendar.containsKey(targetDate));
    }
}

