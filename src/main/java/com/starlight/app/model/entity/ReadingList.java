package com.starlight.app.model.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ReadingList {
    private List<Books> currentlyReading = new ArrayList<>();
    private List<Books> finished = new ArrayList<>();
    private List<Books> tbr = new ArrayList<>();

    public void addBook(UserListType listType, Books books) {
        switch (listType) {
            case CURRENT -> currentlyReading.add(books);
            case FINISHED -> finished.add(books);
            case TBR -> tbr.add(books);
        }
    }
}
