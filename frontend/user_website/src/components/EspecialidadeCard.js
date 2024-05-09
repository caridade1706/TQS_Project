import React from 'react';
import './EspecialidadeCard.css'; 

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faLocationDot, faPhone } from '@fortawesome/free-solid-svg-icons';

const EspecialidadeCard = ({ image, name, city, number, location }) => {
  return (
    <div className="especialidade-card">
      <div className="especialidade-image">
        <img src={image} alt={name} />
      </div>
      <div className="especialidade-info">
        <p>{city}</p>
        <h2>{name}</h2>
    
        <p><FontAwesomeIcon icon={faPhone} className="navbar-icon"/>{number}</p>
    
        <p><FontAwesomeIcon icon={faLocationDot} className="navbar-icon"/> {location}</p>
      </div>
    </div>
  );
};

export default EspecialidadeCard;
