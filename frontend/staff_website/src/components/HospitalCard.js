import React from 'react';
import './HospitalCard.css'; 

const HospitalCard = ({ image, name, city, number, location }) => {
  return (
    <div className="hospital-card">
      <div className="hospital-image">
        <img src={image} alt={name} />
      </div>
      <div className="hospital-info">
        <p>{city}</p>
        <h2>{name}</h2>
        <p>{number}</p>
        <p>{location}</p>
      </div>
    </div>
  );
};

export default HospitalCard;
