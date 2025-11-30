package com.starlight.app.model;

import java.time.LocalDate;
import java.util.List;

public record StreakSummary(
        int currentStreak,
        int longestStreak,
        LocalDate lastRead,
        List<Calendar> calendar
) {}
