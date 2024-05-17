import React from "react";
import { Link } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import "./Navbar.css";
import { useAuth } from "../context/AuthContext/AuthProvider";


import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faHospital } from "@fortawesome/free-solid-svg-icons";

const Navbar = () => {
  const navigate = useNavigate();
  const { isLoggedIn, logout } = useAuth();
  
  const handleLogout = () => {
    logout();
    navigate("/");
  }

  return (
    <nav className="navbar">
      <div className="navbar-text">
        <Link to="/" style={{ textDecoration: 'none', color: 'black' }}>
          <FontAwesomeIcon icon={faHospital} className="navbar-icon" />
          CliniConnect
        </Link>
      </div>
      <div className="navbar-item">
        {isLoggedIn ? (
          <>
            <Link to="/consultas">
              <button className="navbar-button">Consultas</button>
            </Link>
            <Link to="/perfil">
              <button className="navbar-button">Perfil</button>
            </Link>
            <button className="navbar-button" onClick={handleLogout}>Sair</button>
          </>
        ) : (
          <>
            <Link to="/login">
              <button className="navbar-button">Entrar</button>
            </Link>
            <Link to="/register">
              <button className="navbar-button">Registar</button>
            </Link>
          </>
        )}
      </div>
    </nav>
  );
};

export default Navbar;
