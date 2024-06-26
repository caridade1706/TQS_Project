import { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { Link } from "react-router-dom";
import "./Login.css";
import { useAuth } from "../../context/AuthContext/AuthProvider";

function Login(props) {
  const navigate = useNavigate();
  const { login } = useAuth(); // Use o método login do contexto
  const [credentials, setCredentials] = useState({});
  const [error, setError] = useState(""); // State to manage login errors

  const handleChange = (e) => {
    setCredentials({
      ...credentials,
      [e.target.name]: e.target.value,
    });
  };

  const handleLogin = async () => {
    try {
      const response = await axios.post(
        process.env.REACT_APP_API_URL + "staff/login",
        credentials
      );
      if (response.data) {
        const token  = response.data.token;
        login(credentials.email, token); 
        navigate("/staffpage");
      }
    } catch (error) {
      console.error("Login failed:", error);
      setError("Invalid login. Please try again."); // Set error state on login failure
    }
  };

  return (
    <div className="login-page">
      <div className="login-container">
        <h1 className="title">Staff Login</h1>
        <div className="input-container">
          <input
            type="text"
            name="email"
            onChange={handleChange}
            placeholder="Enter email"
            className="input"
          />
          <input
            type="password"
            name="password"
            onChange={handleChange}
            placeholder="Enter password"
            className="input"
          />
        </div>

        <button onClick={handleLogin} className="login-button">
          Login
        </button>

        {error && (
          <div className="error-message">
            {error}
          </div>
        )}

        <div style={{ textAlign: "center", marginTop: "20px" }}>
          <Link to="/signup" className="link-signin">
            Don't have an account? Sign Up
          </Link>
        </div>
      </div>
    </div>
  );
}

export default Login;
