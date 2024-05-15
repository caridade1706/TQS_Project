import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import './App.css';

import Navbar from './components/Navbar';
import HomeHospitalPage from './pages/HomeHospitalPage';
import HomeMedicoPage from './pages/HomeMedicoPage';
import HomeEspecialidadePage from './pages/HomeEspecialidadePage';
import ConsultasPage from './pages/ConsultasPage';
import AgendamentoPage from './pages/AgendamentoPage';
import Login from './pages/Login';
import Register from './pages/Register';

function App() {
  return (
    <Router>
      <div className="App">
        <Navbar />
        <Routes>
          <Route path="/" element={<HomeHospitalPage />} />
          <Route path="/hospitais" element={<HomeHospitalPage />} />
          <Route path="/medicos" element={<HomeMedicoPage />} />
          <Route path="/especialidades" element={<HomeEspecialidadePage />} />
          <Route path="/consultas" element={<ConsultasPage />} />
          <Route path="/agendamento" element={<AgendamentoPage />} />
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
