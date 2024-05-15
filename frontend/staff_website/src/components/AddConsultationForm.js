import React, { useState } from 'react';
import './AddConsultationForm.css'; 

function AddConsultationForm({ addConsultation }) {
    const [formData, setFormData] = useState({
        number: '', name: '', date: '', time: '', doctor: '', specialty: '', hospital: 'Hospital de Aveiro'
    });

    const handleChange = (e) => {
        setFormData({...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        addConsultation(formData);
        setFormData({ number: '', name: '', date: '', time: '', doctor: '', specialty: '', hospital: 'Hospital de Aveiro' });
    };

    return (
        <div className="form-consultation-container">
            <form onSubmit={handleSubmit} className="form-container">
                <div className="form-row">
                    <input type="text" name="number" value={formData.number} onChange={handleChange} placeholder="User Number" required />
                    <input type="text" name="name" value={formData.name} onChange={handleChange} placeholder="User Name" required />
                </div>
                <div className="form-row">
                    <input type="date" name="date" value={formData.date} onChange={handleChange} required style={{ minWidth: '47.5%'}}/>
                    <input type="time" name="time" value={formData.time} onChange={handleChange} required style={{ minWidth: '47.5%'}}/>
                </div>
                <div className="form-row">
                    <input type="text" name="doctor" value={formData.doctor} onChange={handleChange} placeholder="Doctor" required />
                    <input type="text" name="specialty" value={formData.specialty} onChange={handleChange} placeholder="Specialty" required />
                </div>
                <div className="form-row">
                    <input type="text" name="hospital" value={formData.hospital} onChange={handleChange} required disabled />
                </div>
                <button className="add-consultation-btn" type="submit">Add Consultation</button>
            </form>
        </div>
    );
}

export default AddConsultationForm;
