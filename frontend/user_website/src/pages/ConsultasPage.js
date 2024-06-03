import React, { useState, useEffect } from "react";
import Tab from "../components/Tab/Tab";
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

  const handleCancel = (id) => {
    axios
      .delete(process.env.REACT_APP_API_URL + `appointments/${id}`)
      .then(() => {
        setFutureAppointments(
          futureAppointments.filter((fetchFutureAppointment) => fetchFutureAppointment.id !== id)
        );
      })
      .catch((error) => console.error("Failed to cancel appointment", error));
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
            <th>Status</th>
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

                {appointment.status === "CREATED" ? (
                  <>
                    <td>
                      <span style={{ color: "#5AA7FF" }}>◉</span>{" "}
                      {appointment.status}
                    </td>
                    <td>
                      <button
                        className="table-consultations-btn btn-cancel"
                        onClick={() => handleCancel(appointment.id)}
                      >
                        Cancel
                      </button>
                    </td>
                  </>
                ) : appointment.status === "WAITING" ? (
                  <>
                    <td>
                      <span style={{ color: "#FFCB2F" }}>◉</span>{" "}
                      {appointment.status}
                    </td>
                    <td>
                    </td>
                  </>
                ) : (
                  <>
                    <td>
                      <span style={{ color: "#49C493" }}>◉</span>{" "}
                      {appointment.status}
                    </td>
                    <td>
                      <span></span>
                    </td>
                  </>
                )}
              </tr>
            ))
          )}
        </tbody>
      </table>
    </div>
  );
};

export default HomeHospitalPage;
