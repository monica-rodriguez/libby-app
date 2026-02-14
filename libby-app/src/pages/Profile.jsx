import React, { useState, useEffect } from 'react';
import './Profile.css';
import BookCard from '../components/BookCard';

function Profile() {
  const user = {
    name: 'Book Lover',
    email: 'booksslover@example.com',
    joined: 'September 2025',
  };

  const [books, setBooks] = useState([]);
  const [tbrList, setTbrList] = useState([]);
  const [checkedOutList, setCheckedOutList] = useState([]);
  const [finishedList, setFinishedList] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    async function loadBooks() {
      try {
        const res = await fetch('http://localhost:8080/starlightFolklore/api/getAll');
        const data = await res.json();
        setBooks(data);
      } catch (err) {
        console.error('Error fetching books:', err);
      } finally {
        setLoading(false);
      }
    }
    loadBooks();
  }, []);

  if (loading) return <div>Loading books...</div>;

  const tbrCount = 8;
  const checkedOutCount = 3;
  const finishedCount = 5;

  const showTBR = () => {
    setTbrList(books.slice(0, tbrCount));
    setCheckedOutList([]);
    setFinishedList([]);
  };

  const showCheckedOut = () => {
    setCheckedOutList(books.slice(tbrCount, tbrCount + checkedOutCount));
    setTbrList([]);
    setFinishedList([]);
  };

  const showFinished = () => {
    setFinishedList(books.slice(tbrCount + checkedOutCount, tbrCount + checkedOutCount + finishedCount));
    setTbrList([]);
    setCheckedOutList([]);
  };

  return (
    <div className="profile-page">
      <h1>{user.name}'s Profile</h1>
      <p>Email: {user.email}</p>
      <p>Member Since {user.joined}</p>

      <h2>Reading Stats</h2>
      <div className="stats-bubbles">
        <button className="bubble tbr" onClick={showTBR}>
          TBR: {tbrCount}
        </button>
        <button className="bubble checkedOut" onClick={showCheckedOut}>
          Checked Out: {checkedOutCount}
        </button>
        <button className="bubble finished" onClick={showFinished}>
          Finished: {finishedCount}
        </button>
      </div>

      <div className="book-section">
        {tbrList.length > 0 && (
          <>
            <h3>TBR Books</h3>
            <div className="book-grid">
              {tbrList.map((book, i) => (
                <BookCard key={i} book={book} />
              ))}
            </div>
          </>
        )}

        {checkedOutList.length > 0 && (
          <>
            <h3>Checked Out Books</h3>
            <div className="book-grid">
              {checkedOutList.map((book, i) => (
                <BookCard key={i} book={book} />
              ))}
            </div>
          </>
        )}

        {finishedList.length > 0 && (
          <>
            <h3>Finished Books</h3>
            <div className="book-grid">
              {finishedList.map((book, i) => (
                <BookCard key={i} book={book} />
              ))}
            </div>
          </>
        )}
      </div>
    </div>
  );
}

export default Profile;