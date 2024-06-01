import React, { useState, useEffect } from 'react';
import axios from 'axios';
import AddConsultationForm from '../components/AddConsultationForm';
import Modal from '../components/Modal/Modal';

import './ConsultationsTable.css';

function ConsultationsTable() {
    const [showForm, setShowForm] = useState(false);
    const [consultations, setConsultations] = useState([]);
    const [filter, setFilter] = useState('');
    var x= 2;

    useEffect(() => {
        const fetchConsultations = () => {
            axios.get('http://localhost:8080/api/appointments/today')
                .then(response => {
                    if (response.status === 204 || response.data.length === 0) {
                        setConsultations([]);
                    } else {
                        setConsultations(response.data);
                    }
                })
                .catch(error => console.error('Failed to fetch consultations', error));
        };

        fetchConsultations();
    });    

    const formatDate = (dateString) => {
        const options = { year: 'numeric', month: '2-digit', day: '2-digit' };
        return new Date(dateString).toLocaleDateString('pt-PT', options);
    };

    const formatTime = (timeString) => {
        const options = { hour: 'numeric', minute: 'numeric' };
        return new Date(`1970-01-01T${timeString}`).toLocaleTimeString('pt-PT', options);
    };

    const formateDate = (dateString) => {
        const date = new Date(dateString);
        return `${date.getFullYear()}-${String(date.getMonth() + 1).padStart(2, '0')}-${String(date.getDate()).padStart(2, '0')}`;
    };

    const filteredConsultations = consultations.filter(consultation =>
        Object.values(consultation).some(value => {
            if (typeof value === 'object' && value!== null && 'name' in value) {
                return value.name.toString().toLowerCase().includes(filter.toLowerCase());
            }
            if (typeof value === 'number' && 'patientNumber' in consultation.patient) {
                return String(consultation.patient.patientNumber).toString().toLowerCase().includes(filter.toLowerCase());
            }
            if (typeof value === 'string' &&!isNaN(Date.parse(value))) {
                const formattedDate = formatDate(value);
                return formattedDate.toLowerCase().includes(filter.toLowerCase());
            }
            return value.toString().toLowerCase().includes(filter.toLowerCase());
        })
    );

    const handleCheckIn = (id) => {
        axios.post(`http://localhost:8080/api/appointments/${id}/WAITING`)
            .then(() => {
                setConsultations(consultations.map(consultation =>
                    consultation.id === id ? { ...consultation, status: 'WAITING' } : consultation
                ));
            })
            .catch(error => console.error('Failed to check-in', error));
    };

    const handlePayment = (id) => {
        axios.post(`http://localhost:8080/api/appointments/${id}/PAYED`)
            .then(() => {
                setConsultations(consultations.map(consultation =>
                    consultation.id === id ? { ...consultation, status: 'PAYED' } : consultation
                ));
            })
            .catch(error => console.error('Failed to check-in', error));
    };

    const handleCancel = (id) => {
        axios.delete(`http://localhost:8080/api/appointments/${id}`)
            .then(() => {
                setConsultations(consultations.filter(consultation => consultation.id !== id));
            })
            .catch(error => console.error('Failed to cancel appointment', error));
    };

    return (
        <div className="table-consultation-container">
            
            <button className='new-consultation-btn' onClick={() => setShowForm(true)}>
                    New Consultation
                </button>
                <Modal show={showForm} onClose={() => setShowForm(false)}>
                    <AddConsultationForm
                        onClose={() => setShowForm(false)}
                        onAddConsultation={consultation => setConsultations([...consultations, consultation])}
                    />
                </Modal>

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
                        <th>Status</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    {consultations.length === 0 ? (
                        <tr>
                            <td colSpan="9">
                                <h2>There are no appointments for today!</h2>
                            </td>
                        </tr>
                    ) : (
                        filteredConsultations.map(consultation => (
                            <tr key={consultation.id}>
                                <td>{consultation.patient.patientNumber}</td>
                                <td>{consultation.patient.name}</td>
                                <td>{formatDate(consultation.date)}</td>
                                <td>{formatTime(consultation.time)}</td>
                                <td>{consultation.doctor.name}</td>
                                <td>{consultation.doctor.speciality}</td>
                                <td>{consultation.hospital.name}</td>

                                {consultation.status === 'CREATED'? (
                                    <>
                                        <td><span style={{color: "#5AA7FF"}}>◉</span> {consultation.status}</td>
                                        <td>
                                            <button className='table-consultations-btn btn-checkin' onClick={() => handleCheckIn(consultation.id)}>Check-In</button>
                                            <button className='table-consultations-btn btn-cancel' onClick={() => handleCancel(consultation.id)}>Cancel</button>
                                        </td>
                                    </>
                                ) : consultation.status === 'WAITING'? (                                        
                                    <>
                                        <td><span style={{color: "#FFCB2F"}}>◉</span> {consultation.status}</td>
                                        <td>
                                            <button className='table-consultations-btn btn-pay' onClick={() => handlePayment(consultation.id)}>Pay</button>
                                            <button className='table-consultations-btn btn-cancel' onClick={() => handleCancel(consultation.id)}>Cancel</button>
                                        </td>
                                    </>
                                ) : (                              
                                    <> 
                                        <td><span style={{color: "#49C493"}}>◉</span> {consultation.status}</td>
                                        <td><span></span></td>
                                    </>
                                )}
                            </tr>
                        ))
                    )}
                </tbody>

            </table>
        </div>
    );
}

export default ConsultationsTable;
