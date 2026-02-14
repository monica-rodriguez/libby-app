import React from "react";
import "./Chatbot.css";
import design from "../assets/SF_Chatbot_Design(1).png";

export default function ChatbotBubble({ onClick }) {
  return (
    <div className="chatbot-bubble" onClick={onClick}>
      <img 
        src={design} 
        alt="Chatbot Design" 
        className="chatbot-image" 
        style={{ width: "80%", height: "90%" }}
        />
    </div>
  );
}