import React from 'react';
import './SuccessModal.css';

const SuccessModal = ({ message, onClose }) => {
  return (
    <div className="success-modal">
      <div className="modal-content">
        <span className="close-button" onClick={onClose}>&times;</span>
        <p>{message}</p>
      </div>
    </div>
  );
};

export default SuccessModal;