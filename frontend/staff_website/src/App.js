import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import "./App.css";
import { AuthProvider } from "./context/AuthContext/AuthProvider";

import Navbar from "./components/Navbar";
import HomePage from "./pages/HomePage/HomePage";
import Login from "./pages/Login/Login";
import Register from "./pages/Register/Register";
import StaffPage from "./pages/StaffPage/StaffPage";
import ProfilePage from "./pages/ProfilePage/ProfilePage";

function App() {
  return (
    <Router>
      <AuthProvider>
        <div className="App">
          <Navbar />
          <Routes>
            <Route path="/" element={<HomePage />} />
            <Route path="/signin" element={<Login />} />
            <Route path="/signup" element={<Register />} />
            <Route path="/staffpage" element={<StaffPage />} />
            <Route path="/profil" element={<ProfilePage />} />
          </Routes>
        </div>
      </AuthProvider>
    </Router>
  );
}

export default App;
