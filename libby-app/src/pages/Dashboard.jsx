import React, { useState, useEffect } from "react";
import "./Dashboard.css";

export default function Dashboard() {
  const [chartUrl, setChartUrl] = useState(null);
  const [loading, setLoading]   = useState(true);
  const [error, setError]       = useState(null);

  useEffect(() => {
    fetch("/starlightFolklore/charts/genre")
      .then((res) => {
        if (!res.ok) throw new Error("Failed to load chart data");
        return res.json();
      })
      .then((data) => {
        setChartUrl(data.chartUrl);
        setLoading(false);
      })
      .catch((err) => {
        setError(err.message);
        setLoading(false);
      });
  }, []);

  return (
    <div className="dashboard">
      {/* Starfield background dots */}
      <div className="stars" aria-hidden="true">
        {Array.from({ length: 60 }).map((_, i) => (
          <span key={i} className="star" style={{
            left: `${Math.random() * 100}%`,
            top:  `${Math.random() * 100}%`,
            animationDelay: `${(Math.random() * 4).toFixed(2)}s`,
            width:  `${Math.random() > 0.8 ? 3 : 2}px`,
            height: `${Math.random() > 0.8 ? 3 : 2}px`,
          }} />
        ))}
      </div>

      <header className="dash-header">
        <div className="dash-title-group">
          <span className="dash-glyph">✦</span>
          <div>
            <h1 className="dash-title">Starlight Collection</h1>
          </div>
        </div>
        <div className="dash-badge">Live Data</div>
      </header>

      <main className="dash-main">
        <div className="chart-card">
          <div className="chart-card-header">
            <h2 className="chart-card-title">Books by Genre</h2>
            <p className="chart-card-desc">
              Distribution of titles across all genres in the Starlight collection
            </p>
          </div>

          <div className="chart-frame">
            {loading && (
              <div className="chart-loading">
                <div className="loading-orb" />
                <span>Summoning chart data…</span>
              </div>
            )}

            {error && (
              <div className="chart-error">
                <span>⚠</span>
                <p>{error}</p>
                <small>Make sure your backend is running on port 8080</small>
              </div>
            )}

            {chartUrl && !loading && (
              <img
                src={chartUrl}
                alt="Books by Genre"
                className="chart-img"
              />
            )}
          </div>

          {chartUrl && (
            <div className="chart-actions">
              <a
                href={chartUrl}
                download="starlight-genre-chart.png"
                target="_blank"
                rel="noreferrer"
                className="btn-download"
              >
                ↓ Save chart
              </a>
            </div>
          )}
        </div>
      </main>
    </div>
  );
}
