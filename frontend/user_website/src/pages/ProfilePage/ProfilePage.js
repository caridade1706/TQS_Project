import React, { useEffect, useState } from "react";
import axios from "axios";
import "./ProfilePage.css";

const ProfilePage = () => {
  const [userDetails, setUserDetails] = useState({});
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
        console.log("User details:", response.data);
        setUserDetails(response.data.patient);
      } catch (error) {
        console.error("Failed to fetch user details:", error);
      }
    };

    fetchUserDetails();
  }, [token]);

  const formatDate = (dateString) => {
    const options = { year: "numeric", month: "numeric", day: "numeric" };
    return new Date(dateString).toLocaleDateString(undefined, options);
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
              <h2>Prefered Hospital:</h2>
              <p>{userDetails.preferredHospital}</p>
            </div>

            <div className="info-button">
              <button className="save-button">
                Save
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ProfilePage;
