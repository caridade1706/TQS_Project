import React from "react";
import { useNavigate } from "react-router-dom";

import "./HomePage.css";

const HomePage = () => {
  const navigate = useNavigate();

  const handleTicketPage = () => {
    navigate("/queue");
  };

  const handleChooseTicketPage = () => {
    navigate("/chosseTicket");
  };

  return (
    <div className="home-page">
      <div className="buttons-container">
        <button className="button" onClick={handleTicketPage} >
          Take a Ticket Page
        </button>
        <button className="button" onClick={handleChooseTicketPage} >Go to the Queue Management Dashboard</button>
      </div>
    </div>
  );
};

export default HomePage;
