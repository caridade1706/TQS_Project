import React from "react";
import "./QueuePage.css";

const queueData = {
  A: {
    lastCalled: { ticketNumber: "A100", counter: 1 },
    previousCalled: [
      { ticketNumber: "A99", counter: 6 },
      { ticketNumber: "A98", counter: 7 },
    ],
  },
  B: {
    lastCalled: { ticketNumber: "B100", counter: 4 },
    previousCalled: [
      { ticketNumber: "B99", counter: 1 },
      { ticketNumber: "B98", counter: 3 },
    ],
  },
  C: {
    lastCalled: { ticketNumber: "C100", counter: 2 },
    previousCalled: [
      { ticketNumber: "C99", counter: 4 },
      { ticketNumber: "C98", counter: 2 },
    ],
  },
  D: {
    lastCalled: { ticketNumber: "D100", counter: 5 },
    previousCalled: [
      { ticketNumber: "D99", counter: 2 },
      { ticketNumber: "D98", counter: 3 },
    ],
  },
};

const QueuePage = () => {
  return (
    <div className="queue-dashboard">
      <div className="card-container">
        {Object.entries(queueData).map(([type, info], index) => (
          <div className="card" key={index}>
            <h2>Ticket Type: {type}</h2>
            <div className="last-called">
              <p>Last Called: {info.lastCalled.ticketNumber}</p>
              <p>Counter: {info.lastCalled.counter}</p>
            </div>
            <div className="previous-called">
              <h3>Previously Called:</h3>
              {info.previousCalled.map((ticket, idx) => (
                <p key={idx}>
                  {ticket.ticketNumber} at Counter {ticket.counter}
                </p>
              ))}
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default QueuePage;
