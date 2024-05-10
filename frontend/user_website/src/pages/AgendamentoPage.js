import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import './AgendamentoPage.css';

const AgendamentoPage = () => {
    const [hospital, setHospital] = useState('');
    const [doctor, setDoctor] = useState('');
    const [specialty, setSpecialty] = useState('');
    const [time, setTime] = useState('');
    const [date, setDate] = useState('');

    const handleSubmit = (event) => {
        event.preventDefault();
        // Aqui você pode adicionar a lógica para enviar os dados do formulário
        console.log({ hospital, doctor, specialty, time, date });
    };

    return (
        <div className="user-page">
            
            <Link to="/consultas"><button className="userpage-button up-consultas-button">Consultas</button></Link>
            <Link to="/historico"><button className="userpage-button up-historico-button">Histórico</button></Link>
            <Link to="/agendamento"><button className="userpage-button up-agendamento-button up-agendamento-button-active">Agendamento</button></Link>
            <Link to="/pesquisa"><button className="userpage-button up-pesquisa-button">Hospitais</button></Link>

            <form onSubmit={handleSubmit} className="appointment-form">
                <div className="form-group">
                    <label htmlFor="hospital">Hospital</label>
                    <input type="text" id="hospital" value={hospital} onChange={(e) => setHospital(e.target.value)} />
                </div>
                <div className="form-group">
                    <label htmlFor="doctor">Médico</label>
                    <input type="text" id="doctor" value={doctor} onChange={(e) => setDoctor(e.target.value)} />
                </div>
                <div className="form-group">
                    <label htmlFor="specialty">Especialidade</label>
                    <input type="text" id="specialty" value={specialty} onChange={(e) => setSpecialty(e.target.value)} />
                </div>
                <div className="form-group">
                    <label htmlFor="time">Horário</label>
                    <input type="time" id="time" value={time} onChange={(e) => setTime(e.target.value)} />
                </div>
                <div className="form-group">
                    <label htmlFor="date">Data</label>
                    <input type="date" id="date" value={date} onChange={(e) => setDate(e.target.value)} />
                </div>
                <button type="submit" className="submit-button">Agendar</button>
            </form>
        </div>
    );
};

export default AgendamentoPage;
