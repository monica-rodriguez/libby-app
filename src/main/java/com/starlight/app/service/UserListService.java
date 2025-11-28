package com.starlight.app.service;

import com.starlight.app.model.entity.Books;
import com.starlight.app.model.entity.ReadingList;
import com.starlight.app.model.entity.UserListType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@AllArgsConstructor
@Service
public class UserListService {

    private final BookService bookService;

    private final Map<String, ReadingList> userLists = new ConcurrentHashMap<>();

    public ReadingList getUserLists(String userId) {
        return userLists.computeIfAbsent(userId, id -> new ReadingList());
    }

    public ReadingList addToUserList(String userId, String bookId, UserListType listType) {
        Books book = bookService.getByIndex(bookId);

        ReadingList readingList = getUserLists(userId);
        readingList.addBook(listType, book);

        return readingList;
    }
}

