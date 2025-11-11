import React, { useState, useEffect } from 'react'
import { useParams } from 'react-router-dom'

function BookDetail() {
    const { id } = useParams()
    const [book, setBook] = useState(null)

    useEffect(() => {
        fetch('')
            .then(res => res.json())
            .then(data => setBook(data))
            .catch(err => console.error(err))
    }, [id])

    if (!book) return <p>Loading...</p>

    return (
        <div>
            <h1>{book.title}</h1>
            <p><strong>Author:</strong> {book.author}</p>
            <p><strong>Outline:</strong> {book.outline}</p>
        </div>
  )
}

export default BookDetail