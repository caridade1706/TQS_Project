import React, { useState } from 'react';
import './ConsultationsTable.css';
import AddConsultationForm from '../components/AddConsultationForm';

function ConsultationsTable() {

    const initialConsultations = [
        { number: 1, name: 'John Doe', date: '2024-05-09', time: '14:00', doctor: 'Dr. Smith', specialty: 'Cardiology', hospital: 'City Hospital' },
        { number: 2, name: 'Jane Smith', date: '2024-05-10', time: '09:00', doctor: 'Dr. Adams', specialty: 'Dermatology', hospital: 'Regional Health Center' }
    ];

    const [consultations, setConsultations] = useState(initialConsultations);
    const [showForm, setShowForm] = useState(false); // Estado para controlar a visibilidade do formulário

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
