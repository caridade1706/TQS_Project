import React, { useState } from "react";
import "./ChooseTicket.css"; // Ensure you link the correct CSS file

const ChooseTicket = () => {
  const [selectedType, setSelectedType] = useState("");

  const handleSelectTicket = (type) => {
    setSelectedType(type);
    alert(type);
  };

  return (
    <div className="choose-ticket-container">
      <h1 className="heading">Choose Your Ticket</h1>
        <div className="button-container">
          <button
            onClick={() => handleSelectTicket("Type A")}
            className="ticket-button"
          >
            Type A
          </button>
          <button
            onClick={() => handleSelectTicket("Type B")}
            className="ticket-button"
          >
            Type B
          </button>
          <button
            onClick={() => handleSelectTicket("Type C")}
            className="ticket-button"
          >
            Type C
          </button>
        </div>
    </div>
  );
};

export default ChooseTicket;
