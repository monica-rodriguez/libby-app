import React from 'react'
import './Profile.css'

function Profile() {
  const user = {
    name: 'Book Lover',
    email: 'booksslover@example.com',
    joined: 'September 2025',
    stats: {
      checkedOut: 3,
      tbr: 8,
      finished: 5,
    },
  }

  return (
    <div className="profile-page">
      <h1>{user.name}'s Profile</h1>
      <p>Email: {user.email}</p>
      <p>Member Since {user.joined}</p>

      <h2>Reading Stats</h2>
      <ul>
        <li>Checked Out: {user.stats.checkedOut}</li>
        <li>TBR: {user.stats.tbr}</li>
        <li>Finished Books: {user.stats.finished}</li>
      </ul>
    </div>
  )
}

export default Profile