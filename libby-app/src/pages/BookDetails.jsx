import React from "react";

function BookDetail({ book }) {
  if (!book) {
    return <p>No book selected.</p>;
  }

  return (
    <div>
      <h1>{book.bookTitle}</h1>
      <p>
        <strong>Author:</strong> {book.firstName} {book.lastName}
      </p>
      <p>
        <strong>Genre:</strong> {book.bookGenre}
      </p>
    </div>
  );
}

export default BookDetail;
