import React from "react";
import "./Chatbot.css";

export default function ChatbotBubble({ onClick }) {
  return (
    <div className="chatbot-bubble" onClick={onClick}>
      ✨
    </div>
  );
}