import React, { useState } from 'react';

function ConsultationsTable({ consultations }) {
    const [filter, setFilter] = useState('');

    const filteredConsultations = consultations.filter(consultation =>
        Object.values(consultation).some(value =>
            value.toString().toLowerCase().includes(filter.toLowerCase())
        )
    );

    return (
        <div>
            <input type="text" placeholder="Search..." onChange={e => setFilter(e.target.value)} />
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
