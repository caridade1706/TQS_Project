import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { Link } from "react-router-dom";
import "./Register.css";

function Register() {
  const navigate = useNavigate();
  
  const [name, setName] = useState("");
  const [dob, setDob] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [phone, setPhone] = useState("");
  const [address, setAddress] = useState("");
  const [city, setCity] = useState("");
  const [department, setDepartment] = useState("");
  const [task, setTask] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post(
        "http://localhost:8080/api/staff/register",
        {
          name,
          dob,
          email,
          password,
          phone,
          address,
          city,
          department,
          task
        }
      );
      console.log(response.data);
      alert("Registration successful");
      navigate('/signin');
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <div className="register-page">
      <div className="register-container">
        <h1 className="title-register">Register</h1>

        <div className="input-container-register">
          <div
            style={{ width: "30rem", display: "flex", flexDirection: "column" }}
          >
            <input
              type="text"
              name="Name"
              value={name}
              onChange={(e) => setName(e.target.value)}
              placeholder="Name"
              className="input-register"
              required
            />
            <input
              type="date"
              name="Date of Birth"
              value={dob}
              onChange={(e) => setDob(e.target.value)}
              placeholder="Date of Birth"
              className="input-register"
              required
            />
            <input
              type="email"
              name="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              placeholder="Email"
              className="input-register"
              required
            />
            <input
              type="password"
              name="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              placeholder="Password"
              className="input-register"
              required
            />
          </div>
          <div
            style={{ width: "30rem", display: "flex", flexDirection: "column" }}
          >
            <input
              type="text"
              name="phone"
              value={phone}
              onChange={(e) => setPhone(e.target.value)}
              placeholder="Phone"
              className="input-register"
              required
            />
            <input
              type="text"
              name="address"
              value={address}
              onChange={(e) => setAddress(e.target.value)}
              placeholder="Address"
              className="input-register"
              required
            />
            <input
              type="text"
              name="city"
              value={city}
              onChange={(e) => setCity(e.target.value)}
              placeholder="City"
              className="input-register"
              required
            />
            <input
              type="text"
              name="Department"
              value={department}
              onChange={(e) => setDepartment(e.target.value)}
              placeholder="Department"
              className="input-register"
              required
            />
            <input
              type="text"
              name="Task"
              value={task}
              onChange={(e) => setTask(e.target.value)}
              placeholder="Task"
              className="input-register"
              required
            />
          </div>
        </div>
        <button onClick={handleSubmit} className="register-button">
          Register
        </button>
        <Link to="/signin" className="link">
          Already have an account? Login
        </Link>
      </div>
    </div>
  );
}

export default Register;