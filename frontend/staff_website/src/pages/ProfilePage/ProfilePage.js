import React, { useEffect, useState } from "react";
import axios from "axios";
import "./ProfilePage.css";
import Modal from "react-modal";

const ProfilePage = () => {
  const [userDetails, setUserDetails] = useState({});
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [availableHospitals, setAvailableHospitals] = useState([]);
  const [selectedHospitals, setSelectedHospitals] = useState([]);
  const token = localStorage.getItem("token");

  useEffect(() => {
    // Function to fetch user details using the token
    const fetchUserDetails = async () => {
      try {
        const email = localStorage.getItem("email"); // Assuming email is stored in localStorage
        const response = await axios.get(
          process.env.REACT_APP_API_URL + `staff/${email}`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );
        setUserDetails(response.data);
        setSelectedHospitals(response.data.hospitals || []);
      } catch (error) {
        console.error("Failed to fetch user details:", error);
      }
    };

    const fetchAvailableHospitals = async () => {
      try {
        const response = await axios.get(
          `http://localhost:8080/api/hospitals/`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );
        console.log(response.data);
        setAvailableHospitals(response.data);
      } catch (error) {
        console.error("Failed to fetch available hospitals:", error);
      }
    };

    fetchUserDetails();
    fetchAvailableHospitals();
  }, [token]);

  const formatDate = (dateString) => {
    const options = { year: "numeric", month: "numeric", day: "numeric" };
    return new Date(dateString).toLocaleDateString(undefined, options);
  };

  const openModal = () => {
    setIsModalOpen(true);
  };

  const closeModal = () => {
    setIsModalOpen(false);
  };

  const addHospital = (hospital) => {
    if (!selectedHospitals.includes(hospital)) {
      setSelectedHospitals([...selectedHospitals, hospital]);
    }
    closeModal();
  };

  const saveHospital = async () => {
    if (selectedHospitals.length > 0) {
      try {
        const response = await axios.post(
          `http://localhost:8080/api/staff/addHospitals`,
          { hospitals: selectedHospitals },
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );
        if (response.status === 200) {
          alert("Hospitals updated successfully");
        }
      } catch (error) {
        console.error("Failed to update hospitals:", error);
        alert("Failed to update hospitals");
      }
    }
    else {
        alert("There are no hospitals selected!")
    }
  };

  return (
    <div className="profile-page">
      <div className="box-info">
        <h1>Profile Information</h1>
        <div className="info">
          <div className="info-column">
            <div className="info-item">
              <h2>Name:</h2>
              <p>{userDetails.name}</p>
            </div>
            <div className="info-item">
              <h2>Email:</h2>
              <p>{userDetails.email}</p>
            </div>
            <div className="info-item">
              <h2>Date of Birth:</h2>
              <p>{formatDate(userDetails.dob)}</p>
            </div>
            <div className="info-item">
              <h2>Address:</h2>
              <p>{userDetails.address}</p>
            </div>
            <div className="info-item">
              <h2>City:</h2>
              <p>{userDetails.city}</p>
            </div>
          </div>
          <div className="info-column">
            <div className="info-item">
              <h2>Phone:</h2>
              <p>{userDetails.phone}</p>
            </div>
            <div className="info-item">
              <h2>Department:</h2>
              <p>{userDetails.department}</p>
            </div>
            <div className="info-item">
              <h2>Task:</h2>
              <p>{userDetails.task}</p>
            </div>
            <div className="info-item">
              <h2>Hospitals:</h2>
              {selectedHospitals.length > 0 ? (
                selectedHospitals.map((hospital) => (
                    <p>{hospital.name}</p>
                ))
              ) : (
                <p>There are no hospitals selected!</p>
              )}
            </div>

            <div className="info-button">
              <button onClick={openModal} className="add-hospitals-button">
                Add More Hospitals
              </button>
              <button onClick={saveHospital} className="save-button">
                Save
              </button>
            </div>
          </div>
        </div>
      </div>

      <Modal isOpen={isModalOpen} onRequestClose={closeModal} className="modal">
        <h2>Select a Hospital</h2>
        <ul className="hospital-list">
          {availableHospitals.map((hospital) => (
            <li key={hospital.id} onClick={() => addHospital(hospital.name)}>
              {hospital.name}
            </li>
          ))}
        </ul>
        <button onClick={closeModal} className="close-modal-button">
          Close
        </button>
      </Modal>
    </div>
  );
};

export default ProfilePage;
