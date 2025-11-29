import React, { useState } from "react";

import Header from "./components/Header";
import Home from "./pages/Home";
import Search from "./pages/Search";
import BookDetail from "./pages/BookDetails";
import Profile from "./pages/Profile";
import Lists from "./pages/Lists";
import "./App.css";

function App() {
  const [activeTab, setActiveTab] = useState("home");

  return (
    <div className="app-container">
      <Header activeTab={activeTab} setActiveTab={setActiveTab} />
      <main>
        {activeTab === "home" && <Home />}
        {activeTab === "search" && <Search />}
        {activeTab === "lists" && <Lists />}
        {activeTab === "bookDetail" && <BookDetail />}
        {activeTab === "profile" && <Profile />}
      </main>
      <footer>
        <p>© 2025 Starlight Folklore</p>
      </footer>
    </div>
  );
}

export default App;
