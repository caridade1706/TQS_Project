import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import './UserPage.css';

const HomeHospitalPage = () => {

    const appointments = [
        {
            specialty: 'Cardiologia',
            doctor: 'Dr. João',
            hospital: 'Hospital ABC',
            date: '20/04/2023',
            rebook: 'Reagendar'
        },
        {
            specialty: 'Cardiologia',
            doctor: 'Dr. João',
            hospital: 'Hospital ABC',
            date: '20/04/2023',
            rebook: 'Reagendar'
        },
        {
            specialty: 'Cardiologia',
            doctor: 'Dr. João',
            hospital: 'Hospital ABC',
            date: '20/04/2023',
            rebook: 'Reagendar'
        },
    ];

    return (
        <div className="user-page">
            
            <Link to="/consultas"><button className="userpage-button up-consultas-button up-consultas-button-active">Consultas</button></Link>
            <Link to="/historico"><button className="userpage-button up-historico-button">Histórico</button></Link>
            <Link to="/agendamento"><button className="userpage-button up-agendamento-button">Agendamento</button></Link>
            <Link to="/pesquisa"><button className="userpage-button up-pesquisa-button">Hospitais</button></Link>

            <table className="appointment-table">
                <thead>
                    <tr>
                        <th>Especialidade</th>
                        <th>Médico</th>
                        <th>Hospital</th>
                        <th>Data</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    {appointments.map((appointment, index) => (
                        <tr key={index}>
                            <td>{appointment.specialty}</td>
                            <td>{appointment.doctor}</td>
                            <td>{appointment.hospital}</td>
                            <td>{appointment.date}</td>
                            <td><button className="rebook-button">{appointment.rebook}</button></td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

export default HomeHospitalPage;
