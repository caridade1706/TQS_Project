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

function ConsultationsTable() {
    const [consultations, setConsultations] = useState([]);
    const [hospitalFilter, setHospitalFilter] = useState('');
    const [specialtyFilter, setSpecialtyFilter] = useState('');

    useEffect(() => {
        console.log("Buscando consultas de hoje...");
        const fetchConsultations = () => {
            axios.get('http://localhost:8080/api/appointments/today')
                .then(response => {
                    console.log("Status da resposta:", response.status);
                    if (response.status === 204 || response.data.length === 0) {
                        console.log("Sem consultas para o dia atual.");
                        setConsultations([]);
                    } else {
                        setConsultations(response.data);
                        console.log("Consultas filtradas:", response.data);
                    }
                })
                .catch(error => console.error('Failed to fetch consultations', error));
        };

        fetchConsultations();
    }, [hospitalFilter, specialtyFilter]);

    const formatDate = (dateString) => {
        const options = { year: 'numeric', month: '2-digit', day: '2-digit' };
        return new Date(dateString).toLocaleDateString('pt-PT', options);
    };

    const formatTime = (timeString) => {
        const options = { hour: 'numeric', minute: 'numeric' };
        return new Date(`1970-01-01T${timeString}`).toLocaleTimeString('pt-PT', options);
    };

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

    const [filter, setFilter] = useState('');

    const filteredConsultations = consultations.filter(consultation =>
        Object.values(consultation).some(value => {
            
            // Verifica se o valor é um objeto e tem uma propriedade 'name'
            if (typeof value === 'object' && value!== null && 'name' in value) {
                console.log("BBBBBBBBBBBBBBBBBBBBBB");
                console.log(value.name.toString().toLowerCase());
                console.log(filter.toLowerCase());

                console.log(value);
                return value.name.toString().toLowerCase().includes(filter.toLowerCase());
            }
            // Verifica se o valor é um objeto e tem uma propriedade 'patientNumber'
            if (typeof value === 'object' && value!== null && 'patientNumber' in value) {
                console.log("CCCCCCCCCCCCCCCCCCCC");
                console.log(value.patientNumber.toString().toLowerCase());
                console.log(filter.toLowerCase());
                return value.patientNumber.toString().toLowerCase().includes(filter.toLowerCase());
            }
            // Caso contrário, aplica a filtragem normalmente
            console.log("AAAAAAAAAAAAAAAAAAAAAAAA");
            console.log(value.toString().toLowerCase());
            console.log(filter.toLowerCase());
            return value.toString().toLowerCase().includes(filter.toLowerCase());
        })
    );

    return (
        <div className="table-consultation-container">
            

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
                                <td>{consultation.status}</td>

                                <td>
                                    {consultation.status === 'CREATED'? (
                                        <>
                                            <button onClick={() => handleCheckIn(consultation.id)}>Check-In</button>
                                            <button onClick={() => handleCancel(consultation.id)}>Cancel</button>
                                        </>
                                    ) : consultation.status === 'WAITING'? (                                        
                                        <>
                                            <button onClick={() => handlePayment(consultation.id)}>Pay</button>
                                            <button onClick={() => handleCancel(consultation.id)}>Cancel</button>
                                        </>
                                    ) : (                              
                                        <> 
                                        </>
                                    )}
                                </td>
                            </tr>
                        ))
                    )}
                </tbody>

            </table>
        </div>
    );
}

export default ConsultationsTable;
