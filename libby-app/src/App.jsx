import React, { useState } from "react";

import Header from "./components/Header";
import Home from "./pages/Home";
import Search from "./pages/Search";
import BookDetail from "./pages/BookDetails";
import Profile from "./pages/Profile";
import Dashboard from "./pages/Dashboard";
import "./App.css";

function App() {
  const [activeTab, setActiveTab] = useState("home");

  return (
    <div className="app-container">
      <Header activeTab={activeTab} setActiveTab={setActiveTab} />
      <main>
        {activeTab === "home" && <Home setActiveTab={setActiveTab} />}
        {activeTab === "search" && <Search />}
        {activeTab === "bookDetail" && <BookDetail />}
        {activeTab === "profile" && <Profile />}
        {activeTab === "dashboard" && <Dashboard />}
      </main>
      <footer>
        <p>© 2025 Starlight Folklore</p>
      </footer>
    </div>
  );
}

export default App;