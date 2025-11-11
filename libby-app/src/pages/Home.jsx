import React, { useState, useEffect } from 'react'
import BookCard from '../components/BookCard'
import './Home.css';

function Home() {
    const [books, setBooks] = useState([]);
    const [loading, setLoading] = useState(true);

  //backend endpoint to fetch books 
    useEffect(() => {
        fetch('http://localhost:8080/starlightFolklore/getAll') //backend endpoint
            .then(res => res.json())
            .then(data => {
                setBooks(data);
                setLoading(false);
              })
            .catch(err => console.error(err))
    }, []);

    if (loading) {
        return <div className="loading">Loading...</div>;
    }

  return (
    <div className="home-container">
        <h1>All Books</h1>
        <div className="book-list">
            {books.map(book => <BookCard key={book.id} book={book} />)}
        </div>
    </div>
  )
}

export default Home