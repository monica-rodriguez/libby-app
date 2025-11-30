package com.starlight.app.controller;

import com.starlight.app.service.StreakService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/starlightFolklore/api/streaks")
@RequiredArgsConstructor
public class StreakController {

    private final StreakService streakService;

    @PostMapping("/log")
    public void logReading(
            @RequestParam String userId,
            @RequestParam String bookId,
            @RequestParam(required = false) LocalDate date
    ) {
        streakService.logReading(userId, bookId, date);
    }

    @GetMapping("/current")
    public int getCurrentStreak(@RequestParam String userId) {
        return streakService.getCurrentStreak(userId);
    }

    @GetMapping("/calendar")
    public Map<LocalDate, String> getCalendar(
            @RequestParam String userId,
            @RequestParam int year,
            @RequestParam int month
    ) {
        return streakService.getMonthlyCalendar(userId, year, month);
    }
}
