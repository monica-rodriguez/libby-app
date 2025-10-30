import React from 'react'
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom'
import Header from './components/Header'
import Home from './pages/Home'
import Search from './pages/Search'
import BookDetail from './pages/BookDetail'
import Profile from './pages/Profile'
import Lists from './pages/Lists'

function App() {
  return (
    <Router>
      <Header />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/search" element={<Search />} />
        <Route path="/book/:id" element={<BookDetail />} />
        <Route path="/profile" element={<Profile />} />
        <Route path="/lists" element={<Lists />} />
      </Routes>
    </Router>
  )
}

export default App
