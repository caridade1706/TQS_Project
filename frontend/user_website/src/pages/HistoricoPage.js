import React, { useState, useEffect } from "react";
import Tab from "../components/Tab/Tab";
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
      const email = localStorage.getItem("email");
      const response = await axios.get(
        `http://localhost:8080/api/appointments/history/${email}`
      );

      if (Array.isArray(response.data)) {
        setAppointments(response.data);
      } else {
        console.error("Response data is not an array:", response.data);
      }
    } catch (error) {
      console.error("Failed to fetch past appointments:", error);
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

      <Tab userName={userDetails.name} />

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
          {appointments.length === 0 ? (
            <tr>
              <td colSpan="9">
                <h2>There were no appointments booked!</h2>
              </td>
            </tr>
          ) : (
            appointments.map((appointment, index) => (
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

export default AppointmentsHistory;
