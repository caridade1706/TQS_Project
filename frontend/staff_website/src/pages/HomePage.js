import React, { useState } from 'react';
import CounterSelector from '../components/CounterSelector';
import CategoryDisplay from '../components/CategoryDisplay';
import ConsultationsTable from '../components/ConsultationsTable';
import AddConsultationForm from '../components/AddConsultationForm';
import Navbar from '../components/Navbar';

function HomePage() {
    // Example data for consultations
    const initialConsultations = [
        { number: 1, name: 'John Doe', date: '2024-05-09', time: '14:00', doctor: 'Dr. Smith', specialty: 'Cardiology', hospital: 'City Hospital' },
        { number: 2, name: 'Jane Smith', date: '2024-05-10', time: '09:00', doctor: 'Dr. Adams', specialty: 'Dermatology', hospital: 'Regional Health Center' }
    ];

    const [consultations, setConsultations] = useState(initialConsultations);

    const addConsultation = (consultation) => {
        setConsultations([...consultations, consultation]);
    };

    const handleCounterSelect = (counter) => {
        console.log("Selected counter:", counter);  // Just for demonstration
    };

    return (
        <div>
            <div className="container">
                <CounterSelector onSelectCounter={handleCounterSelect} />
                <CategoryDisplay />
                <ConsultationsTable consultations={consultations} />
                <hr />
                <h3>Add Consultation:</h3>
                <AddConsultationForm addConsultation={addConsultation} />
            </div>
        </div>
    );
}

export default HomePage;
