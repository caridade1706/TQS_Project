import React, { useState } from "react";
import axios from "axios";
import Modal from "react-modal";
import "./ChooseTicket.css"; // Ensure you link the correct CSS file

Modal.setAppElement('#root');

const ChooseTicket = () => {

  const [modalIsOpen, setModalIsOpen] = useState(false);
  const [modalContent, setModalContent] = useState("");

  const handleSelectTicket = (type) => {
    var hospitalName = "Aveiro's Hospital";
    var url = process.env.REACT_APP_API_URL + 'queue-management?priorityStatus=' + type + '&hospitalName=' + hospitalName;
    console.log(url);
    axios
      .post(url)
      .then((response) => {
        setModalContent(
          <>
            <h2>Ticket added successfully!</h2><br />
            <p>Your ticket number is <strong>{response.data.queueNumber}.</strong></p><br />
            <p>Please wait for your turn!</p>
          </>
        );
        setModalIsOpen(true);
        console.log(response.data);
      })
      .catch((error) => {
        console.error("Error adding hospital:", error);
        alert("Failed to add hospital");
      });
  };

  return (
    <div className="choose-ticket-container">
      <h1 className="heading">Choose Your Ticket</h1>
      <div className="button-container">
        <button
          onClick={() => handleSelectTicket("A")}
          className="ticket-button"
        >
          Type A
        </button>
        <button
          onClick={() => handleSelectTicket("B")}
          className="ticket-button"
        >
          Type B
        </button>
        <button
          onClick={() => handleSelectTicket("C")}
          className="ticket-button"
        >
          Type C
        </button>
      </div>
      <Modal
        isOpen={modalIsOpen}
        onRequestClose={() => setModalIsOpen(false)}
        contentLabel="Ticket Response Modal"
        className="modal"
        overlayClassName="modal-overlay"
      >
        <h2>{modalContent}</h2>
        <button onClick={() => setModalIsOpen(false)}>Close</button>
      </Modal>
    </div>
  );
};

export default ChooseTicket;
