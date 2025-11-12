package com.starlight.app.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


//@Data
public class Books {
    @JsonProperty("FirstNames")
    private String firstName;

    @JsonProperty("LastNames")
    private String lastName;

    @JsonProperty("Title")
    private String bookTitle;

    @JsonProperty("Genre")
    private String bookGenre;

    public List<String> getAuthors() {
        List<String> author = new ArrayList<>();

        if (firstName == null || lastName == null) {
            return author;
        }

        String[] fn = firstName.contains("/") ? firstName.split("/") : new String[]{firstName};
        String[] ln = lastName.contains("/") ? lastName.split("/") : new String[]{lastName};

        int length = Math.min(fn.length, ln.length);
        for (int i = 0; i < length; i++) {
            author.add(fn[i].trim() + " " + ln[i].trim());
        }

        if(author.isEmpty() && !firstName.isBlank()) {
            author.add(firstName.trim() + (lastName.isBlank() ? "" : " " + lastName.trim()));
        }
        return author;
    }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getBookGenre() { return bookGenre; }
    public String getBookTitle() { return bookTitle; }


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setBookTitle(String bookTitle)  {
        this.bookTitle = bookTitle;
    }
    public void setBookGenre(String bookGenre) {
        this.bookGenre = bookGenre;
    }

}
