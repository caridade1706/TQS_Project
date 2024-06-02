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
        `${process.env.REACT_APP_API_URL}appointments/future/${email}`,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      );
      console.log(response.data);
      setFutureAppointments(response.data); // Supondo que a resposta contenha um campo 'appointments'
    } catch (error) {
      console.error("Failed to fetch future appointments:", error);
    }
  };

  const formatDate = (dateString) => {
    const options = { year: "numeric", month: "2-digit", day: "2-digit" };
    return new Date(dateString).toLocaleDateString("pt-PT", options);
  };

  const formatTime = (timeString) => {
    const options = { hour: "numeric", minute: "numeric" };
    return new Date(`1970-01-01T${timeString}`).toLocaleTimeString(
      "pt-PT",
      options
    );
  };

  return (
    <div className="user-page">
      <h1 style={{ textAlign: "start", marginLeft: "20rem" }}>
        Welcome back, {userDetails.name}!
      </h1>
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
            <th>Speciality</th>
            <th>Doctor</th>
            <th>Hospital</th>
            <th>Date</th>
            <th>Time</th>
            <th></th>
          </tr>
        </thead>

        <tbody>
          {futureAppointments.length === 0 ? (
            <tr>
              <td colSpan="9">
                <h2>There are no appointments booked!</h2>
              </td>
            </tr>
          ) : (
            futureAppointments.map((appointment, index) => (
              <tr key={index}>
                <td>{appointment.type}</td>
                <td>{appointment.doctor.name}</td>
                <td>{appointment.hospital.name}</td>
                <td>{formatDate(appointment.date)}</td>
                <td>{formatTime(appointment.time)}</td>

                <td>
                  <button className="rebook-button">Reagendar</button>
                </td>
              </tr>
            ))
          )}
        </tbody>
      </table>
    </div>
  );
};

export default HomeHospitalPage;
