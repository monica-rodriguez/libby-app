package com.starlight.app.model.entity;

import lombok.Data;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
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

    public List<Books> getBooks(UserListType listType) {
        return switch (listType) {
            case CURRENT -> currentlyReading;
            case FINISHED -> finished;
            case TBR -> tbr;
        };
    }

    public List<Books> getCurrent() {
        return currentlyReading;
    }
}
