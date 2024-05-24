import React from "react";
import { Link } from "react-router-dom";
import "./HomePage.css";
import { useAuth } from "../../context/AuthContext/AuthProvider";

const HomePage = () => {
  const { isLoggedIn, logout } = useAuth();

  return (
    <div className="home-page">
      <div className="box-welcome">
        <h1 style={{ fontSize: "4vh" }}>
          Welcome <br /> to the Staff Interface!
        </h1>

        {!isLoggedIn ? (
          <div className="box-container">
            <Link to="/signin">
              <button className="box-button">Sing In</button>
            </Link>
            <Link to="/signup">
              <button className="box-button">Sing Up</button>
            </Link>
          </div>
        ) : (
          <div className="box-container">
            <h3>
              Go to the Staff Page to start <br /> Managing the Appointments and more!
            </h3>
          </div>
        )}
      </div>
    </div>
  );
};

export default HomePage;
