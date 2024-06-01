import React, { useState, useEffect } from 'react';
import axios from 'axios';

function AppointmentsHistory({ userId }) {
    const [appointments, setAppointments] = useState([]);

    useEffect(() => {
        axios.get(`http://localhost:8080/api/appointments/history/${userId}`)
            .then(response => {
                setAppointments(response.data);
            })
            .catch(error => {
                console.error('Failed to fetch past appointments', error);
            });
    }, [userId]);

    return (
        <div>
            <h1>Historical Appointments</h1>
            {appointments.length > 0 ? (
                <ul>
                    {appointments.map(appointment => (
                        <li key={appointment.id}>
                            {appointment.date} - {appointment.time} - {appointment.doctor.name}
                        </li>
                    ))}
                </ul>
            ) : (
                <p>No past appointments found.</p>
            )}
        </div>
    );
}

export default AppointmentsHistory;
