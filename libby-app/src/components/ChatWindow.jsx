import React, { useState } from "react";
import "./Chatbot.css";

export default function ChatWindow({ onClose }) {
  const [messages, setMessages] = useState([
    { sender: "bot", text: "Hello dreamer, what story are you searching for?" }
  ]);
  const [input, setInput] = useState("");

  const sendMessage = () => {
    if (!input.trim()) return;

    const newMessage = { sender: "user", text: input };
    setMessages([...messages, newMessage]);

    // call backend search endpoint here
    // fetch(`/starlightFolklore?title=${input}`)

    setInput("");
  };

  return (
    <div className="chat-window">
      <div className="chat-header">
        <span>✨ Starlight Guide</span>
        <button onClick={onClose}>✖</button>
      </div>

      <div className="chat-body">
        {messages.map((msg, i) => (
          <div key={i} className={`msg ${msg.sender}`}>
            {msg.text}
          </div>
        ))}
      </div>

      <div className="chat-input">
        <input
          type="text"
          placeholder="Ask about a book..."
          value={input}
          onChange={(e) => setInput(e.target.value)}
          onKeyDown={(e) => e.key === "Enter" && sendMessage()}
        />
        <button onClick={sendMessage}>➤</button>
      </div>
    </div>
  );
}