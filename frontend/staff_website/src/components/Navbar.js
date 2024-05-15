import React from 'react';
import './Navbar.css';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faHospital } from '@fortawesome/free-solid-svg-icons';

const Navbar = () => {
  return (
    <nav className="navbar">
        <div className="navbar-text">
            <FontAwesomeIcon icon={faHospital} className="navbar-icon"/>
            Clini Connect
        </div>
        <button className="navbar-button">Logout</button>
    </nav>
  );
};

export default Navbar;
