import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Home from '../pages/Home';
import BookDetails from '../pages/BookDetails';
import BookCard from './BookCard';

function App() {
  const sampleBook = { id: 1, title: "Sample Book", author: "Author Name" };

  return (
    <Router>
      <div className="App">
        <h1>Libby App</h1>
        
        {/* Example usage of BookCard */}
        <BookCard book={sampleBook} />

        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/book/:id" element={<BookDetails />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
