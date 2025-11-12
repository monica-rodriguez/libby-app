import React from 'react';
import './BookCard.css';
import { Link } from 'react-router-dom';

function BookCard({ book }) {
  if (!book) return null;

  return (
    <div className="book-card">
      <h3>{book.Title}</h3>
      <p>Author: {book.FirstNames} {book.LastNames}</p>
      <p>Genre: {book.Genre}</p>

      <Link to={'/book/${book.id}'}>View Details</Link>
    </div>
  );
}

export default BookCard;
