import React, { useState, useEffect } from 'react'
import BookCard from '../components/BookCard'
import './Lists.css' //import CSS file

function Lists() {
    const [activeTab, setActiveTab] = useState('checkedOut')
    const [lists, setLists] = useState({ checkedOut: [], tbr: [], finished: [] })
    const [loading, setLoading] = useState(true)
    
    useEffect(() => {
        fetch('http://localhost:8080/starlightFolklore/getAll') //backend endpoint
            .then(res => res.json())
            .then(data => {
              setLists(data)
              setLoading(false)
            })
            .catch(err => console.error(err))
    }, [])

    const renderBooks = () => {
      if (!lists[activeTab] || lists[activeTab].length === 0) {
        return <p>No books in this list.</p>
      } 
      return lists[activeTab].map(book => <BookCard key={book.id} book={book} />)
    }
    if (loading) {
      return <p className="loading">Loading...</p>
    }

  return (
    <div className="lists-container">
      <h1>My Lists</h1>
      <div className="tabs">
        <button 
          className={activeTab === 'checkedOut' ? 'active' : ''}
          onClick={() => setActiveTab('checkedOut')}
        >
          Checked Out
        </button>
        <button 
          className={activeTab === 'tbr' ? 'active' : ''}
          onClick={() => setActiveTab('tbr')}
        >
          To Be Read
        </button>
        <button 
          className={activeTab === 'finished' ? 'active' : ''}
          onClick={() => setActiveTab('finished')}
        >
          Finished
        </button>
      </div>
      <div className="book-list">{renderBooks()}</div>
    </div>
  )
}

export default Lists