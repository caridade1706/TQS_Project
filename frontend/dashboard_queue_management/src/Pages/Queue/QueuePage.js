import React, { useState, useEffect, useRef } from "react";
import axios from "axios";
import Modal from "react-modal";
import "./QueuePage.css";

const initialQueueData = {
  A: {
    lastCalled: { ticketNumber: "", counter: "" },
    previousCalled: [],
  },
  B: {
    lastCalled: { ticketNumber: "", counter: "" },
    previousCalled: [],
  },
  C: {
    lastCalled: { ticketNumber: "", counter: "" },
    previousCalled: [],
  },
};

const QueuePage = () => {
  const [data, setData] = useState(() => {
    const savedData = localStorage.getItem("queueData");
    return savedData ? JSON.parse(savedData) : initialQueueData;
  });
  const [modalIsOpen, setModalIsOpen] = useState(false);
  const [modalContent, setModalContent] = useState("");
  const audioRef = useRef(null);

  useEffect(() => {
    const fetchQueueData = () => {
      axios
        .get(process.env.REACT_APP_API_URL + "queue-management")
        .then((response) => {
          const ticket = response.data;
          console.log("Fetched queue data:", ticket);
          const updatedData = { ...data };

          if (ticket && ticket.priorityStatus) {
            const type = ticket.priorityStatus;
            if (ticket.queueNumber !== updatedData[type].lastCalled.ticketNumber) {
              setModalContent(
                <div style={{ textAlign: 'center', justifyContent: 'center' }}>
                  <h1>Next Ticket Called</h1>
                  <h2 style={{ fontWeight: 'normal' }}>Ticket: <strong>{ticket.queueNumber}</strong></h2>
                  <h2 style={{ fontWeight: 'normal' }}>Counter: <strong>{ticket.counterNumber}</strong></h2>
                </div>
              );
              setModalIsOpen(true);
              audioRef.current.play();

              if (updatedData[type].lastCalled.ticketNumber) {
                updatedData[type].previousCalled.unshift(updatedData[type].lastCalled);
              }
              updatedData[type].lastCalled = {
                ticketNumber: ticket.queueNumber,
                counter: ticket.counterNumber,
              };
            }
          }

          setData(updatedData);
          localStorage.setItem("queueData", JSON.stringify(updatedData));

          setTimeout(() => {
            setModalIsOpen(false);
          }, 5000);
        })
        .catch((error) => {
          console.error("Error fetching queue data:", error);
        });
    };

    fetchQueueData(); // Initial fetch
    const interval = setInterval(fetchQueueData, 5000); // Fetch every 5 seconds

    return () => clearInterval(interval); // Cleanup interval on component unmount
  }, [data]);

  return (
    <div className="queue-dashboard">
      <audio ref={audioRef} src="/notification.mp3" preload="auto" />
      <div className="card-container">
        {Object.entries(data).map(([type, info], index) => (
          <div className="card" key={index}>
            <h2>Ticket Type: {type}</h2>
            <div className="last-called">
              <p>Last Called: {info.lastCalled.ticketNumber}</p>
              <p>Counter: {info.lastCalled.counter}</p>
            </div>
            <div className="previous-called">
              <h3>Previously Called:</h3>
              {info.previousCalled
                .filter(ticket => ticket.ticketNumber) // Filter out empty ticket numbers
                .map((ticket, idx) => (
                  <p key={idx}>
                    {ticket.ticketNumber} at Counter {ticket.counter}
                  </p>
                ))}
            </div>
          </div>
        ))}
      </div>
      <Modal
        isOpen={modalIsOpen}
        onRequestClose={() => setModalIsOpen(false)}
        contentLabel="Ticket Call Modal"
        className="modal"
        overlayClassName="modal-overlay"
      >
        <h2>{modalContent}</h2>
      </Modal>
    </div>
  );
};

export default QueuePage;