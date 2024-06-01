import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import axios from "axios";

import "./UserPage.css";

const AppointmentsHistory = ({ userId }) => {
    
  const [userDetails, setUserDetails] = useState({});
  const [appointments, setAppointments] = useState([]); 
  
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
    fetchPastAppointments();
  }, [token]);


    const fetchPastAppointments = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/api/appointments/history/${userId}`);
        console.log(response.data); // Imprime a resposta da API no console
        if (Array.isArray(response.data)) {
          setAppointments(response.data);
        } else {
          console.error("Response data is not an array:", response.data);
        }
      } catch (error) {
        console.error("Failed to fetch past appointments:", error);
      }
    };

/*   const appointments = [
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

      {/* <div>
        <h1>Historical Appointments</h1>
        {appointments.length > 0? (
            <ul>
            {appointments.map(appointment => (
                <li key={appointment.id}>
                {appointment.date} - {appointment.time} - {appointment.doctor.name}
                </li>
            ))}
            </ul>
        ) : (
            <p>No past appointments found.</p>
        )}
      </div> */}

      <table className="appointment-table">
        <thead>
            <tr>
            <th>Especialidade</th>
            <th>Médico</th>
            <th>Hospital</th>
            <th>Data</th>
            <th>Ações</th>
            </tr>
        </thead>
        <tbody>
            {appointments.map((appointment, index) => (
            <tr key={index}>
                <td>{appointment.specialty}</td>
                <td>{appointment.doctor}</td>
                <td>{appointment.hospital}</td>
                <td>{appointment.date}</td>
                <td>
                {/* Aqui você pode adicionar botões ou links para ações relacionadas a cada consulta,
                    como reagendar ou visualizar detalhes. Por exemplo: */}
                <button className="rebook-button">Reagendar</button>
                </td>
            </tr>
            ))}
        </tbody>
        </table>
    </div>
  );
};

export default AppointmentsHistory;
