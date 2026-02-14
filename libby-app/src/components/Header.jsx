import React from 'react'
import './Header.css';
import logo from '../assets/logo.png';

function Header({ activeTab, setActiveTab }) {
  return (
    <header className="header">
      <div className="left-section">
        <img src={logo} alt="Starlight Folklore Logo" className="logo" />
        <h1 className="app-title">Starlight Folklore</h1>
      </div>
      
      <nav className="nav-bar">
        <button className={activeTab === 'home' ? 'active' : ''} onClick={() => setActiveTab('home')}>Home</button>
        <button className={activeTab === 'search' ? 'active' : ''} onClick={() => setActiveTab('search')}>Search</button>
        <button className={activeTab === 'profile' ? 'active' : ''} onClick={() => setActiveTab('profile')}>Profile</button>
      </nav>
    </header>
  )
}

export default Header