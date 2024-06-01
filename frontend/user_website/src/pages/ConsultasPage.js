import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import axios from "axios";

import "./UserPage.css";

const HomeHospitalPage = () => {
  const [userDetails, setUserDetails] = useState({});
  const [futureAppointments, setFutureAppointments] = useState([]);
  const token = localStorage.getItem("token");

  useEffect(() => {
    // Function to fetch user details using the token
    const fetchUserDetails = async () => {
      try {
        const email = localStorage.getItem("email"); // Assuming email is stored in localStorage
        const response = await axios.get(
          process.env.REACT_APP_API_URL + `patients/${email}`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );
        setUserDetails(response.data.patient);
      } catch (error) {
        console.error("Failed to fetch user details:", error);
      }
    };

    fetchUserDetails();
    fetchFutureAppointments(); 
  }, [token]);

  const fetchFutureAppointments = async () => {
    try {
      const email = localStorage.getItem("email");
      const response = await axios.get(
        `${process.env.REACT_APP_API_URL}patients/${email}/future`,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      setFutureAppointments(response.data.appointments); // Supondo que a resposta contenha um campo 'appointments'
    } catch (error) {
      console.error("Failed to fetch future appointments:", error);
    }
  };

  /* const appointments = [
    {
      specialty: "Cardiologia",
      doctor: "Dr. João",
      hospital: "Hospital ABC",
      date: "20/04/2023",
      rebook: "Reagendar",
    },
    {
      specialty: "Cardiologia",
      doctor: "Dr. João",
      hospital: "Hospital ABC",
      date: "20/04/2023",
      rebook: "Reagendar",
    },
    {
      specialty: "Cardiologia",
      doctor: "Dr. João",
      hospital: "Hospital ABC",
      date: "20/04/2023",
      rebook: "Reagendar",
    },
  ]; */

  return (
    <div className="user-page">
      <h1 style={{ textAlign: 'start', marginLeft: '20rem' }}>Welcome back, {userDetails.name}!</h1>
      <Link to="/consultas">
        <button className="userpage-button up-consultas-button up-consultas-button-active">
          Consultas
        </button>
      </Link>
      <Link to="/historico">
        <button className="userpage-button up-historico-button">
          Histórico
        </button>
      </Link>
      <Link to="/agendamento">
        <button className="userpage-button up-agendamento-button">
          Agendamento
        </button>
      </Link>
      <Link to="/pesquisa">
        <button className="userpage-button up-pesquisa-button">
          Hospitais
        </button>
      </Link>

      <table className="appointment-table">
        <thead>
          <tr>
            <th>Especialidade</th>
            <th>Médico</th>
            <th>Hospital</th>
            <th>Data</th>
            <th></th>
          </tr>
        </thead>
        <tbody>
        {futureAppointments.map((appointment, index) => (
          <tr key={index}>
            <td>{appointment.specialty}</td>
            <td>{appointment.doctor}</td>
            <td>{appointment.hospital}</td>
            <td>{appointment.date}</td>
            <td>
              <button className="rebook-button">Reagendar</button>
            </td>
          </tr>
        ))}
        </tbody>
      </table>
    </div>
  );
};

export default HomeHospitalPage;
