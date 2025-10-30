import React, { useState, useEffect } from 'react'
import BookCard from '../components/BookCard'

function Search() {
    const [query, setQuery] = useState('')
    const [allBooks, setAllBooks] = useState([])
    const [results, setResults] = useState([])

    useEffect(() => {
        fetch('') // insert backend
            .then(res => res.json())
            .then(data => setAllBooks(data))
            .catch(err => console.error(err))
    }, [])

    useEffect(() => {
        if (!query) {
            setResults([])
            return
        }
        const filtered = allBooks.filter(book =>
            book.title.toLowerCase().includes(query.toLowerCase()) ||
            book.author.toLowerCase().includes(query.toLowerCase())
        )
        setResults(filtered)
    }, [query, allBooks])

    return (
        <div>
            <h1>Search Books</h1>
            <input
                type="text"
                placeholder="Search by title or author"
                value={query}
                onChange={e => setQuery(e.target.value)}
            />
            <div className="book-results">
            {results.map(book => <BookCard key={book.id} book={book} />)}
            </div>
        </div>
    )
}

export default Search