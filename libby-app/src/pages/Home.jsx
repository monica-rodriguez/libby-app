import React, { useState, useEffect } from "react";
import BookCard from "../components/BookCard";
import "./Home.css";

function Home() {
  const [books, setBooks] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [currentPage, setCurrentPage] = useState(1);

  const BOOKS_PER_PAGE = 10;

  useEffect(() => {
    async function loadBooks() {
      try {
        const res = await fetch(
          "http://localhost:8080/starlightFolklore/getAll"
        );
        if (!res.ok) {
          throw new Error(`Request failed with status ${res.status}`);
        }
        const data = await res.json();
        setBooks(data);
      } catch (err) {
        console.error("Error fetching books:", err);
        setError("Could not load books. Check console for details.");
      } finally {
        setLoading(false);
      }
    }

    loadBooks();
  }, []);

  useEffect(() => {
    setCurrentPage(1);
  }, [books]);

  if (loading) return <div className="loading">Loading...</div>;
  if (error) return <div className="loading">{error}</div>;

  const totalPages = Math.ceil(books.length / BOOKS_PER_PAGE);
  const indexOfLastBook = currentPage * BOOKS_PER_PAGE;
  const indexOfFirstBook = indexOfLastBook - BOOKS_PER_PAGE;
  const currentBooks = books.slice(indexOfFirstBook, indexOfLastBook);

  const goToPreviousPage = () =>
    setCurrentPage((page) => Math.max(1, page - 1));
  const goToNextPage = () =>
    setCurrentPage((page) => Math.min(totalPages, page + 1));


  return (
    <div className="home-container">
      <h1>All Books</h1>

      <div className="book-grid">
        {currentBooks.length === 0 ? (
          <p>No books found.</p>
        ) : (
          currentBooks.map((book, index) => (
            <BookCard key={index} book={book} />
          ))
        )}
      </div>

      {totalPages > 1 && (
        <div className="pagination">
          <button
            onClick={goToPreviousPage}
            disabled={currentPage === 1}
          >
            Previous
          </button>
          <span>
            Page {currentPage} of {totalPages}
          </span>
          <button
            onClick={goToNextPage}
            disabled={currentPage === totalPages}
          >
            Next
          </button>
        </div>
      )}
    </div>
  );
}

export default Home;