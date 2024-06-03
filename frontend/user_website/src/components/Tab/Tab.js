import React from "react";
import { Link, useLocation } from "react-router-dom";
import "./Tab.css";

const Tab = ({userName}) => {

  const location = useLocation();
  const currentPath = location.pathname;

  return (
    <div>
      <h1 style={{ textAlign: "start", marginLeft: "20rem" }}>
        Welcome back, {userName}!
      </h1>
      <Link to="/consultas">
        <button className={`userpage-button up-consultas-button ${currentPath === "/consultas" ? "up-consultas-button-active" : ""}`}>
          Appointments
        </button>
      </Link>
      <Link to="/historico">
        <button className={`userpage-button up-historico-button ${currentPath === "/historico" ? "up-historico-button-active" : ""}`}>
          Past Appointments
        </button>
      </Link>
      <Link to="/agendamento">
        <button className={`userpage-button up-agendamento-button ${currentPath === "/agendamento" ? "up-agendamento-button-active" : ""}`}>
          Book Appointment
        </button>
      </Link>
      <Link to="/pesquisa">
        <button className={`userpage-button up-pesquisa-button ${currentPath === "/pesquisa" ? "up-pesquisa-button-active" : ""}`}>
          See Hospitals
        </button>
      </Link>
    </div>
  );
};

export default Tab;