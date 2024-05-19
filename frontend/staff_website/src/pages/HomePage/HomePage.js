import React from "react";
import { Link } from "react-router-dom";
import "./HomePage.css";

const HomePage = () => {
  return (
    <div className="home-page">
        <div className="box-welcome">
            <h1 style={{ fontSize: '4vh' }}>Welcome <br /> to the  Staff Interface!</h1>
            <div className="box-container">
                <Link to="/signin">
                    <button className="box-button">Sing In</button>
                </Link>
                <Link to="/signup" >
                    <button className="box-button">Sing Up</button>
                </Link>
            </div>
        </div>
    </div>
  );
};

export default HomePage;