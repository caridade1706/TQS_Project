import React from "react";
import { Link, useLocation } from "react-router-dom";
import { useNavigate } from "react-router-dom";
import "./Navbar.css";
import { useAuth } from "../context/AuthContext/AuthProvider";


import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faHospital } from "@fortawesome/free-solid-svg-icons";

const Navbar = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const { isLoggedIn, logout } = useAuth();
  
  const handleLogout = () => {
    logout();
    navigate("/");
  }

  const isActivePage = (path) => {
    return location.pathname === path;
  };

  return (
    <nav className="navbar">
      <div className="navbar-text">
        <Link to="/" style={{ textDecoration: 'none', color: 'black' }}>
          <FontAwesomeIcon icon={faHospital} className="navbar-icon" />
          CliniConnect - Staff Interface
        </Link>
      </div>
      <div className="navbar-item">
        {isLoggedIn ? (
          <>
            <Link to="/staffpage">
              <button className={`navbar-button ${isActivePage("/staffpage")? "active" : ""}`}>Staff Interface</button>
            </Link>
            <Link to="/admin">
              <button className={`navbar-button ${isActivePage("/admin")? "active" : ""}`}>Admin</button>
            </Link>
            <Link to="/profile">
              <button className={`navbar-button ${isActivePage("/profile")? "active" : ""}`}>Profile</button>
            </Link>
            <button className="navbar-button" onClick={handleLogout}>Log Out</button>
          </>
        ) : (
          <>
            <Link to="/signin">
              <button className={`navbar-button ${isActivePage("/signin")? "active" : ""}`}>Sign In</button>
            </Link>
            <Link to="/signup">
              <button className={`navbar-button ${isActivePage("/signup")? "active" : ""}`}>Sign Up</button>
            </Link>
          </>
        )}
      </div>
    </nav>
  );
};

export default Navbar;
