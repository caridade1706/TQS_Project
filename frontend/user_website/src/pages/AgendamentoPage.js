import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import axios from "axios";
import Tab from "../components/Tab/Tab";
import SuccessModal from "../components/SuccessModal/SuccessModal";

import "./AgendamentoPage.css";

const AgendamentoPage = () => {
  const [formData, setFormData] = useState({
    date: "",
    time: "",
    price: 12.0,
    type: "",
    patientName: "",
    patientNumber: "",
    doctorName: "",
    hospitalName: "",
  });

  const [userDetails, setUserDetails] = useState({});
  const [hospitals, setHospitals] = useState([]);
  const [doctors, setDoctors] = useState([]);
  const [filteredDoctors, setFilteredDoctors] = useState([]);
  const token = localStorage.getItem("token");
  const [uniqueSpecialties, setUniqueSpecialties] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [modalMessage, setModalMessage] = useState("");


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
      setFormData((prevState) => ({
        ...prevState,
        patientName: response.data.patient.name,
      }));
    } catch (error) {
      console.error("Failed to fetch user details:", error);
    }
  };

  const fetchHospitals = async () => {
    try {
      const response = await axios.get(process.env.REACT_APP_API_URL + "hospitals/");
      setHospitals(response.data);
    } catch (error) {
      console.error("Erro ao buscar hospitais", error);
    }
  };

  const fetchDoctors = async () => {
    try {
      const response = await axios.get(process.env.REACT_APP_API_URL + "doctors/");
      setDoctors(response.data);
      setFilteredDoctors(response.data); // Initialize filtered doctors
    } catch (error) {
      console.error("Erro ao buscar médicos", error);
    }
  };

  const extractUniqueSpecialties = () => {
    const specialties = doctors
      .map((doctor) => doctor.speciality)
      .filter((value, index, self) => self.indexOf(value) === index);
    console.log("Especialidades Únicas:", specialties);
    setUniqueSpecialties(specialties); // Define uniqueSpecialties como as especialidades únicas
  };

  useEffect(() => { 
    fetchHospitals();
    fetchDoctors();
    fetchUserDetails();
    if (doctors.length > 0) {
      extractUniqueSpecialties();
    }
  }, [token, doctors]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevState) => ({ ...prevState, [name]: value }));

    if (name === "doctorName") {
      const selectedDoctor = doctors.find((doctor) => doctor.name === value);
      if (selectedDoctor) {
        setFormData((prevState) => ({
          ...prevState,
          type: selectedDoctor.speciality,
        }));
      }
    } else if (name === "type") {
      if (value === "") {
        setFilteredDoctors(doctors); // Reset to show all doctors
      } else {
        setFilteredDoctors(
          doctors.filter((doctor) => doctor.speciality === value)
        );
      }
      setFormData((prevState) => ({ ...prevState, doctorName: "" })); // Clear selected doctor if type changes
    }
  };

  const handleSubmit = (event) => {
    event.preventDefault();

    try {
      axios.post(process.env.REACT_APP_API_URL + "appointments", formData, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      setModalMessage("Appointment successfully booked!");
      setShowModal(true);
      setTimeout(() => {setShowModal(false); window.location.reload()}, 3000);
    } catch (error) {
      console.error("Failed to create appointment:", error);
      alert("Error when booking the Appointment. Plase, try again.");
    }
  };

  return (
    <div className="user-page">
      <Tab userName={userDetails.name} />

      <form onSubmit={handleSubmit} className="appointment-form">
        <div className="form-group">
          <select
            name="hospitalName"
            value={formData.hospitalName}
            onChange={handleChange}
            className="select"
            required
          >
            <option value="">Select a Hospital</option>
            {hospitals.map((hospital) => (
              <option key={hospital.id} value={hospital.name}>
                {hospital.name}
              </option>
            ))}
          </select>
        </div>
        <div className="form-group">
          <select
            name="doctorName"
            value={formData.doctorName}
            onChange={handleChange}
            className="select"
            required
          >
            <option value="">Select a Doctor</option>
            {filteredDoctors.map((doctor) => (
              <option key={doctor.id} value={doctor.name}>
                {doctor.name}
              </option>
            ))}
          </select>
        </div>
        <div className="form-group">
          <select
            name="type"
            value={formData.type}
            onChange={handleChange}
            className="select"
            required
          >
            <option value="">Select a Specialty</option>
            {uniqueSpecialties.map((specialty, index) => (
              <option key={index} value={specialty}>
                {specialty}
              </option>
            ))}
          </select>
        </div>
        <div className="form-group">
          <input
            type="time"
            name="time"
            value={formData.time}
            onChange={handleChange}
            required
          />
        </div>
        <div className="form-group">
          <input
            type="date"
            name="date"
            value={formData.date}
            onChange={handleChange}
            required
          />
        </div>
        <button type="submit" className="submit-button">
          Book!
        </button>
      </form>
      {showModal && (
        <SuccessModal message={modalMessage} onClose={() => setShowModal(false)} />
      )}
    </div>
  );
};

export default AgendamentoPage;
