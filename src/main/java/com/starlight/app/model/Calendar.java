package com.starlight.app.model;

import java.time.LocalDate;
import java.util.List;

public record Calendar(
        LocalDate date,
        Long bookId,
        String bookTitle,
        String coverUrl
) {}

