import React from 'react'

function Profile() {
  const user = {
    name: 'Reader 1',
    email: 'reader1@example.com',
    joined: 'October 2025',
    stats: {
      checkedOut: 3,
      finished: 5,
      tbr: 8,
    },
  }

  return (
    <div className="profile-page">
      <h1>{user.name}'s Profile</h1>
      <p>Email: {user.email}</p>
      <p>Member Since: {user.joined}</p>

      <h2>Reading Stats</h2>
      <ul>
        <li>Checked Out: {user.stats.checkedOut}</li>
        <li>Finished Books: {user.stats.finished}</li>
        <li>TBR: {user.stats.tbr}</li>
      </ul>
    </div>
  )
}

export default Profile