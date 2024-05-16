/* import React, { useState } from 'react';
import './ConsultationsTable.css';
import AddConsultationForm from '../components/AddConsultationForm';
import AddHospitalForm from '../components/AddHospitalForm';


function ConsultationsTable() {

    const initialConsultations = [
        { number: 1, name: 'John Doe', date: '2024-05-09', time: '14:00', doctor: 'Dr. Smith', specialty: 'Cardiology', hospital: 'City Hospital' },
        { number: 2, name: 'Jane Smith', date: '2024-05-10', time: '09:00', doctor: 'Dr. Adams', specialty: 'Dermatology', hospital: 'Regional Health Center' }
    ];

    const [consultations, setConsultations] = useState(initialConsultations);
    const [showForm, setShowForm] = useState(false); 
    const [showHospitalForm, setShowHospitalForm] = useState(false);

    const addConsultation = (consultation) => {
        setConsultations([...consultations, consultation]);
    };

    const [filter, setFilter] = useState('');

    const filteredConsultations = consultations.filter(consultation =>
        Object.values(consultation).some(value =>
            value.toString().toLowerCase().includes(filter.toLowerCase())
        )
    );

    return (
        <div className="table-consultation-container">
            <button 
                className="new-consultation-btn"
                onClick={() => setShowForm(!showForm)}
            >
                New Consultation
            </button>
            {showForm && <AddConsultationForm addConsultation={addConsultation} />}

            <button onClick={() => setShowHospitalForm(!showHospitalForm)} className="toggle-button">
                    {showHospitalForm ? 'Hide Add Hospital Form' : 'Show Add Hospital Form'}
                </button>
            {showHospitalForm && <AddHospitalForm />}
            
            <input type="text" placeholder="Search..." className="table-consultation-search" onChange={e => setFilter(e.target.value)} />
            <table>
                <thead>
                    <tr>
                        <th>Number</th>
                        <th>Name</th>
                        <th>Date</th>
                        <th>Time</th>
                        <th>Doctor</th>
                        <th>Specialty</th>
                        <th>Hospital</th>
                    </tr>
                </thead>
                <tbody>
                    {filteredConsultations.map((item, index) => (
                        <tr key={index}>
                            <td>{item.number}</td>
                            <td>{item.name}</td>
                            <td>{item.date}</td>
                            <td>{item.time}</td>
                            <td>{item.doctor}</td>
                            <td>{item.specialty}</td>
                            <td>{item.hospital}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}

export default ConsultationsTable;
 */

import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './ConsultationsTable.css';
import AddConsultationForm from '../components/AddConsultationForm';
import AddHospitalForm from '../components/AddHospitalForm';

function ConsultationsTable() {
    const [consultations, setConsultations] = useState([]);
    const [showForm, setShowForm] = useState(false);
    const [hospitalFilter, setHospitalFilter] = useState('');
    const [specialtyFilter, setSpecialtyFilter] = useState('');
    const [showHospitalForm, setShowHospitalForm] = useState(false);

    // Busca as consultas de hoje e filtra por hospital e especialidade
    useEffect(() => {
        const fetchConsultations = () => {
            axios.get('http://localhost:8080/api/appointments/today')
                .then(response => {
                    const filteredData = response.data.filter(consultation =>
                        consultation.hospital.includes(hospitalFilter) &&
                        consultation.specialty.includes(specialtyFilter)
                    );
                    setConsultations(filteredData);
                })
                .catch(error => console.error('Failed to fetch consultations', error));
        };

        fetchConsultations();
    }, [hospitalFilter, specialtyFilter]);

    const handleCheckIn = (id) => {
        axios.patch(`/api/appointments/${id}/status`, { status: 'esperando' })
            .then(() => {
                setConsultations(consultations.map(consultation =>
                    consultation.id === id ? { ...consultation, status: 'esperando' } : consultation
                ));
            })
            .catch(error => console.error('Failed to check-in', error));
    };

    const handlePayment = (id) => {
        // Supondo que a página de pagamento tratará de atualizar o status para 'pago'
        window.location.href = `/payment/${id}`; // Exemplo de redirecionamento
    };

    const handleCancel = (id) => {
        axios.delete(`/api/appointments/${id}`)
            .then(() => {
                setConsultations(consultations.filter(consultation => consultation.id !== id));
            })
            .catch(error => console.error('Failed to cancel appointment', error));
    };

    return (
        <div className="table-consultation-container">
            <button onClick={() => setShowForm(!showForm)}>
                {showForm ? 'Hide Form' : 'New Consultation'}
            </button>
            {showForm && <AddConsultationForm addConsultation={consultation => setConsultations([...consultations, consultation])} />}

            <button onClick={() => setShowHospitalForm(!showHospitalForm)} className="toggle-button">
                    {showHospitalForm ? 'Hide Add Hospital Form' : 'Show Add Hospital Form'}
                </button>
            {showHospitalForm && <AddHospitalForm />}

            <input
                type="text"
                placeholder="Filter by hospital..."
                onChange={e => setHospitalFilter(e.target.value)}
            />
            <input
                type="text"
                placeholder="Filter by specialty..."
                onChange={e => setSpecialtyFilter(e.target.value)}
            />
            <table>
                <thead>
                    <tr>
                        <th>Number</th>
                        <th>Name</th>
                        <th>Date</th>
                        <th>Time</th>
                        <th>Doctor</th>
                        <th>Specialty</th>
                        <th>Hospital</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    {consultations.map(consultation => (
                        <tr key={consultation.id}>
                            <td>{consultation.number}</td>
                            <td>{consultation.name}</td>
                            <td>{consultation.date}</td>
                            <td>{consultation.time}</td>
                            <td>{consultation.doctor}</td>
                            <td>{consultation.specialty}</td>
                            <td>{consultation.hospital}</td>
                            <td>{consultation.status}</td>
                            <td>
                                <button onClick={() => handleCheckIn(consultation.id)}>Check-In</button>
                                <button onClick={() => handlePayment(consultation.id)}>Pay</button>
                                <button onClick={() => handleCancel(consultation.id)}>Cancel</button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
}

export default ConsultationsTable;