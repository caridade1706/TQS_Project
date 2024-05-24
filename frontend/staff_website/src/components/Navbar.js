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
          CliniConnect - Staff Interface
        </Link>
      </div>
      <div className="navbar-item">
        {isLoggedIn ? (
          <>
            <Link to="/staffpage">
              <button className="navbar-button">Staff Interface</button>
            </Link>
            <Link to="/profil">
              <button className="navbar-button">Profile</button>
            </Link>
            <button className="navbar-button" onClick={handleLogout}>Log Out</button>
          </>
        ) : (
          <>
            <Link to="/signin">
              <button className="navbar-button">Sign In</button>
            </Link>
            <Link to="/signup">
              <button className="navbar-button">Sign Up</button>
            </Link>
          </>
        )}
      </div>
    </nav>
  );
};

export default Navbar;
