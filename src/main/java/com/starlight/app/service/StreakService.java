package com.starlight.app.service;

import com.starlight.app.model.entity.Entry;
import com.starlight.app.model.entity.UserListType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class StreakService {

    private final UserListService userListService;

    private final Map<String, List<Entry>> streaks = new ConcurrentHashMap<>();

    private List<Entry> getEntries(String userId) {
        return streaks.computeIfAbsent(userId, id -> new ArrayList<>());
    }

    public void logReading(String userId, String bookId, LocalDate date) {
        if (date == null) date = LocalDate.now();

        if (!userListService.isBookInList(userId, bookId, UserListType.CURRENT)) {
            throw new IllegalStateException("Book must be in CURRENT list to log streak.");
        }

        List<Entry> entries = getEntries(userId);

        LocalDate finalDate = date;
        entries.removeIf(e -> e.date().equals(finalDate));
        entries.add(new Entry(bookId, date));
    }

    public int getCurrentStreak(String userId) {
        List<Entry> entries = getEntries(userId);
        if (entries.isEmpty()) return 0;

        Set<LocalDate> dates = new HashSet<>();
        for (Entry e : entries) dates.add(e.date());

        int streak = 0;
        LocalDate cursor = LocalDate.now();

        while (dates.contains(cursor)) {
            streak++;
            cursor = cursor.minusDays(1);
        }

        return streak;
    }

    public Map<LocalDate, String> getMonthlyCalendar(String userId, int year, int month) {
        List<Entry> entries = getEntries(userId);

        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

        Map<LocalDate, String> result = new HashMap<>();

        for (Entry e : entries) {
            if (!e.date().isBefore(start) && !e.date().isAfter(end)) {
                result.put(e.date(), e.bookId());
            }
        }

        return result;
    }
}
