import React, { useState } from "react";
import ChatbotBubble from "../components/ChatbotBubble";
import ChatWindow from "../components/ChatWindow";

export default function Chatbot({ setActiveTab }) {
  const [open, setOpen] = useState(false);

  return (
    <>
      {!open && <ChatbotBubble onClick={() => setOpen(true)} />}
      {open && <ChatWindow onClose={() => setOpen(false)} setActiveTab={setActiveTab} />}
    </>
  );
}