import React, { useState, useRef, useEffect } from "react";
import "./Chatbot.css";
import design from "../assets/SF_Chatbot_Design(1).png";

// Keywords that trigger the chart dashboard
const CHART_TRIGGERS = ["chart", "graph", "genre", "dashboard", "stats", "statistics", "data", "visuali"];

function isChartRequest(text) {
  const lower = text.toLowerCase();
  return CHART_TRIGGERS.some((kw) => lower.includes(kw));
}

export default function ChatWindow({ onClose, setActiveTab }) {
  const bodyRef = useRef(null);

  const [messages, setMessages] = useState([
    { sender: "bot", text: "Hello dreamer, what story are you searching for?" }
  ]);
  const [input, setInput]     = useState("");
  const [loading, setLoading] = useState(false);

  // Auto-scroll to latest message
  useEffect(() => {
    if (bodyRef.current) {
      bodyRef.current.scrollTop = bodyRef.current.scrollHeight;
    }
  }, [messages]);

  const addMessage = (sender, text) => {
    setMessages((prev) => [...prev, { sender, text }]);
  };

  const sendMessage = async () => {
    const trimmed = input.trim();
    if (!trimmed || loading) return;

    addMessage("user", trimmed);
    setInput("");
    setLoading(true);

    // Chart / dashboard request
    if (isChartRequest(trimmed)) {
      addMessage("bot", "✦ Opening the genre dashboard for you…");
      setLoading(false);
      setTimeout(() => { onClose(); setActiveTab("dashboard"); }, 800);
      return;
    }

    // Book search — try to match title, genre, first/last name params
    try {
      const params = new URLSearchParams();

      // Simple heuristic: single word → try as lastName, otherwise title
      const words = trimmed.split(" ");
      if (words.length === 1) {
        params.set("lastName", trimmed);
      } else {
        params.set("title", trimmed);
      }

      const res  = await fetch(`/starlightFolklore?${params.toString()}`);
      const data = await res.json();

      if (!res.ok) {
        // Backend returned 400 with a message string
        addMessage("bot", typeof data === "string" ? data : "Our constallations don't align - nothing found for your search.");
      } else if (Array.isArray(data) && data.length === 0) {
        addMessage("bot", "Our stars are not in alignment. Try a genre, author last name, or book title.");
      } else if (Array.isArray(data)) {
        const preview = data.slice(0, 5);
        const lines = preview.map(
        (b) => `• ${b.Title ?? "Unknown"} — ${[b.FirstNames, b.LastNames].filter(Boolean).join(" ")} (${b.Genre ?? "—"})`
        );
        const more    = data.length > 5 ? `\n…and ${data.length - 5} more.` : "";
        addMessage("bot", `The stars align: ${data.length} title${data.length !== 1 ? "s" : ""}:\n\n${lines.join("\n")}${more}`);
      }
    } catch {
      addMessage("bot", "The stars are quiet right now — couldn't reach the collection. Is the backend running?");
    }

    setLoading(false);
  };

  return (
    <div className="chat-window">
      <div className="chat-header">
        <span>🪄 Starlight Guide</span>
        <button onClick={onClose}>✖</button>
      </div>

      <div className="chat-body" ref={bodyRef}>
        {messages.map((msg, i) => (
          <div key={i} className={`msg-row ${msg.sender}`}>
            {msg.sender === "bot" && (
              <div className="avatar">
                <img src={design} alt="Onyx the Starlight Guide" />
              </div>
            )}
            <div className={`msg ${msg.sender}`} style={{ whiteSpace: "pre-wrap" }}>
              {msg.sender === "bot" && <strong>Onyx:</strong>} {msg.text}
            </div>
          </div>
        ))}

        {loading && (
          <div className="msg-row bot">
            <div className="avatar">
              <img src={design} alt="Onyx" />
            </div>
            <div className="msg bot typing">
              <span /><span /><span />
            </div>
          </div>
        )}
      </div>

      <div className="chat-input">
        <input
          type="text"
          placeholder="Ask about a book…"
          value={input}
          onChange={(e) => setInput(e.target.value)}
          onKeyDown={(e) => e.key === "Enter" && sendMessage()}
          disabled={loading}
        />
        <button onClick={sendMessage} disabled={loading}>➤</button>
      </div>
    </div>
  );
}