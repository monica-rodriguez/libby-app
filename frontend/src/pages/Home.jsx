import React, { useState, useEffect } from 'react'
import BookCard from '../components/BookCard'

function Home() {
    const [books, setBooks] = useState([])

  // placeholder data: need to add backend endpoint to fetch books 
    useEffect(() => {
        fetch('') //insert backend endpoint
            .then(res => res.json())
            .then(data => setBooks(data))
            .catch(err => console.error('Error loading books: ', err))
    }, [])

  return (
    <div>
        <h1>All Books</h1>
        <div className="book-list">
            {books.map(book => <BookCard key={book.id} book={book} />)}
        </div>
    </div>
  )
}

export default Home