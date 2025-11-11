import React from 'react'
import './Header.css';

function Header({ activeTab, setActiveTab }) {
  return (
    <header className="header">
      <h1 className="app-title">Starlight Folklore</h1>
      
      <nav className="nav-bar">
        <button className={activeTab === 'home' ? 'active' : ''} onClick={() => setActiveTab('home')}>Home</button>
        <button className={activeTab === 'search' ? 'active' : ''} onClick={() => setActiveTab('search')}>Search</button>
        <button className={activeTab === 'lists' ? 'active' : ''} onClick={() => setActiveTab('lists')}>My Lists</button>
        <button className={activeTab === 'profile' ? 'active' : ''} onClick={() => setActiveTab('profile')}>Profile</button>
      </nav>
    </header>
  )
}

export default Header