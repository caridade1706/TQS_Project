import React from 'react';
import './HospitalCard.css'; 

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faLocationDot, faPhone } from '@fortawesome/free-solid-svg-icons';

const HospitalCard = ({ image, name, city, number, location }) => {
  return (
    <div className="hospital-card">
      <div className="hospital-image">
        <img src={image} alt={name} />
      </div>
      <div className="hospital-info">
        <p>{city}</p>
        <h2>{name}</h2>
    
        <p><FontAwesomeIcon icon={faPhone} className="navbar-icon"/>{number}</p>
    
        <p><FontAwesomeIcon icon={faLocationDot} className="navbar-icon"/> {location}</p>
      </div>
    </div>
  );
};

export default HospitalCard;
