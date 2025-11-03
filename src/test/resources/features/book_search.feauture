Feature: Book Data Search and Query

  Scenario: Search for a book by title
    Given the data file "books.csv" exists
    When I make a GET request to "/books?title=The Hobbit"
    Then the response should contain "The Hobbit"

  Scenario: Search for a book by genre
    Given the data file "books.csv" exists
    When I make a GET request to "/books?genre=Fantasy"
    Then the response should contain "Fantasy"

  Scenario: Search for a book by author's first and last name
    Given the data file "books.csv" exists
    When I make a GET request to "/books?firstName=J.R.R.&lastName=Tolkien"
    Then the response should contain "Tolkien"

  Scenario: Null search parameters return error
    Given the data file "books.csv" exists
    When I make a GET request to "/books"
    Then the error message "Missing search parameters" should appear
