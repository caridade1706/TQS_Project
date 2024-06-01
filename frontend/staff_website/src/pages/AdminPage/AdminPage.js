import React from 'react';
import AddHospitalForm from '../../components/AddHospitalForm';
import AddDoctorForm from '../../components/AddDoctorForm';

import './AdminPage.css';

function AdminPage() {

    return (
        <div className='admin-page'>
            <div className="container-admin">
                <h2>Hospital</h2>
                <AddHospitalForm />
                <h2>Doctor</h2>
                <AddDoctorForm />
            </div>
        </div>
    );
}

export default AdminPage;