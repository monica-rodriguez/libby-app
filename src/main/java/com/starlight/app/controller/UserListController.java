package com.starlight.app.controller;

import com.starlight.app.model.entity.CheckoutBook;
import com.starlight.app.model.entity.ReadingList;
import com.starlight.app.service.UserListService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("starlightFolklore/api/users/{userId}/reading-list")
@CrossOrigin(origins = "http://localhost:3000") //react server
public class UserListController {

    private final UserListService userListService;

    @PostMapping
    public ResponseEntity<ReadingList> addToReadingList(
            @PathVariable String userId,
            @RequestBody CheckoutBook checkout) {

        ReadingList updateReadingList = userListService.addToUserList(
                userId,
                checkout.getBooksId(),
                checkout.getListType()
        );
        return ResponseEntity.ok(updateReadingList);
    }

    @GetMapping
    public ReadingList getUserList(@PathVariable String userId) {
        return userListService.getUserLists(userId);
    }

}
