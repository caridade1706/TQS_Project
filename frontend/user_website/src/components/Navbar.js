import React from 'react';
import { Link } from 'react-router-dom';
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
        <Link to="/login" className='navbar-link'><button className="navbar-button">Entrar</button></Link>
        <Link to="/consultas" className='navbar-link'><button className="navbar-button">Consultas</button></Link>
    </nav>
  );
};

export default Navbar;
