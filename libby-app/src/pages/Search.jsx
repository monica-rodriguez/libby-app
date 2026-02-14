import React, { useState, useEffect } from "react";
import BookCard from "../components/BookCard";
import "./Search.css"; 

function Search() {
  const [query, setQuery] = useState("");
  const [allBooks, setAllBooks] = useState([]);
  const [results, setResults] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  //load all books from backend
  useEffect(() => {
    const loadBooks = async () => {
      setLoading(true);
      try {
        const res = await fetch("http://localhost:8080/starlightFolklore/getAll");
        if (!res.ok) throw new Error(`Request failed with status ${res.status}`);
        const data = await res.json();
        setAllBooks(data);
      } catch (err) {
        console.error(err);
        setError("Could not load books. Check console for details.");
      } finally {
        setLoading(false);
      }
    };
    loadBooks();
  }, []);

  //filter books whenever query changes
  useEffect(() => {
    if (!query) {
      setResults([]);
      return;
    }

    const lowerQuery = query.toLowerCase();
    const filtered = allBooks.filter((book) => {
      const titleMatch = book.Title?.toLowerCase().includes(lowerQuery);
      const authorMatch =
        book.FirstNames?.toLowerCase().includes(lowerQuery) ||
        book.LastNames?.toLowerCase().includes(lowerQuery) ||
        book.authors?.some((a) => a.toLowerCase().includes(lowerQuery));
      const genreMatch = book.Genre?.toLowerCase().includes(lowerQuery);

      return titleMatch || authorMatch || genreMatch;
    });

    setResults(filtered);
  }, [query, allBooks]);

  return (
    <div className="search-page">
      <input
        className="search-input"
        type="text"
        placeholder="Search by title, author, or genre"
        value={query}
        onChange={(e) => setQuery(e.target.value)}
      />

      {loading && <p>Loading...</p>}
      {error && <p>{error}</p>}

      <div className="book-results">
        {results.length > 0
          ? results.map((book) => <BookCard key={book.bookId} book={book} />)
          : query && <p>No books found.</p>}
      </div>
    </div>
  );
}

export default Search;