import React, { useState } from 'react';
import CounterSelector from '../../components/CounterSelector';
import CategoryDisplay from '../../components/CategoryDisplay';
import ConsultationsTable from '../../components/ConsultationsTable';

import './StaffPage.css';

function StaffPage() {
    const [showForm, setShowForm] = useState(false);
    const [consultations, setConsultations] = useState([]);
    const [selectedCounter, setSelectedCounter] = useState('01');

    const handleCounterSelect = (counter) => {
        console.log("Selected counter:", counter);
        setSelectedCounter(counter);
    };

    return (
        <div className='staff-page'>
            <div className="container">
                <CounterSelector onSelectCounter={handleCounterSelect} />
                <CategoryDisplay selectedCounter={selectedCounter} />
                <ConsultationsTable />
            </div>
        </div>
    );
}

export default StaffPage;
