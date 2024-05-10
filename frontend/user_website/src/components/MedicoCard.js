import React from 'react';
import './MedicoCard.css'; 

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faLocationDot, faPhone } from '@fortawesome/free-solid-svg-icons';

const MedicoCard = ({ image, name, city, number, location }) => {
  return (
    <div className="medico-card">
      <div className="medico-image">
        <img src={image} alt={name} />
      </div>
      <div className="medico-info">
        <p>{city}</p>
        <h2>{name}</h2>
    
        <p><FontAwesomeIcon icon={faPhone} className="navbar-icon"/>{number}</p>
    
        <p><FontAwesomeIcon icon={faLocationDot} className="navbar-icon"/> {location}</p>
      </div>
    </div>
  );
};

export default MedicoCard;
