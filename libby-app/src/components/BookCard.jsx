import React from "react";
import "./BookCard.css";

function BookCard({ book }) {
  if (!book) return null;

  return (
    <div className="book-card">
      <h3>{book.bookTitle ?? book.Title}</h3>
      <p>
        <strong>Author:</strong>{" "}
        {(book.firstName ?? book.FirstNames) + " " + (book.lastName ?? book.LastNames)}
      </p>
      <p>
        <strong>Genre:</strong> {book.bookGenre ?? book.Genre}
      </p>
    </div>
  );
}

export default BookCard;