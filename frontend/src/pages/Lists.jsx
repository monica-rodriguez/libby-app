import React, { useState, useEffect } from 'react'
import BookCard from '../components/BookCard'

function Lists() {
    const [activeTab, setActiveTab] = useState('checkedOut')
    const lists = [lists, setLists] = useState({ checkedOut: [], tbr: [], finished: [] })
    
    useEffect(() => {
        fetch('http://localhost:8080/starlightFolklore/getAll') //backend endpoint
            .then(res => res.json())
            .then(data => setLists(data))
            .catch(err => console.error(err))
    }, [])

    const renderBooks = () => {
        return lists[activeTab].map(book => <BookCard key={book.id} book={book} />)
    }

  return (
    <div>
      <h1>My Lists</h1>
      <div>
        <button onClick={() => setActiveTab('checkedOut')}>Checked Out</button>
        <button onClick={() => setActiveTab('tbr')}>To Be Read</button>
        <button onClick={() => setActiveTab('finished')}>Finished</button>
      </div>
      <div className="book-list">{renderBooks()}</div>
    </div>
  )
}

export default Lists