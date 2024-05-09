import React from 'react';
import { useNavigate } from 'react-router-dom';
import './Navbar.css';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faHospital } from '@fortawesome/free-solid-svg-icons';

const Navbar = () => {

  const navigate = useNavigate();

  const handleHome = () => {
    navigate("/");
  }

  return (
    <nav className="navbar">
        <div className="navbar-text">
          <a onClick={handleHome}>
            <FontAwesomeIcon icon={faHospital} className="navbar-icon"/>
            Clini Connect - Dashboard Queue Management
          </a>
        </div>
    </nav>
  );
};

export default Navbar;