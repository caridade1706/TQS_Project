import React, { useState } from 'react';
import CounterSelector from '../../components/CounterSelector';
import CategoryDisplay from '../../components/CategoryDisplay';
import ConsultationsTable from '../../components/ConsultationsTable';
import AddConsultationForm from '../../components/AddConsultationForm';
import AddHospitalForm from '../../components/AddHospitalForm';
import Modal from '../../components/Modal/Modal';

import './StaffPage.css';

function StaffPage() {
    const [showForm, setShowForm] = useState(false);
    const [showHospitalForm, setShowHospitalForm] = useState(false);
    const [consultations, setConsultations] = useState([]);



    const handleCounterSelect = (counter) => {
        console.log("Selected counter:", counter);
    };

    return (
        <div className='staff-page'>
            <div className="container">
                <CounterSelector onSelectCounter={handleCounterSelect} />
                <button className='btn-add' onClick={() => setShowForm(true)}>
                    New Consultation
                </button>
                <Modal show={showForm} onClose={() => setShowForm(false)}>
                    <AddConsultationForm
                        onClose={() => setShowForm(false)}
                        onAddConsultation={consultation => setConsultations([...consultations, consultation])}
                    />
                </Modal>
                <CategoryDisplay />
                <ConsultationsTable />
            </div>
        </div>
    );
}

export default StaffPage;
