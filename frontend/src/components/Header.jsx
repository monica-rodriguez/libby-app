import React from 'react'
import { Link } from 'react-router-dom'

function Header() {
  return (
    <nav>
      <Link to="/">Home</Link> |{' '}
      <Link to="/search">Search</Link> |{' '}
      <Link to="/lists">My Lists</Link> |{' '}
      <Link to="/profile">Profile</Link>
    </nav>
  )
}

export default Header