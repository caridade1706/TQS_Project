import React, { useState } from 'react';
import axios from 'axios';
import './AddConsultationForm.css'; 

function AddConsultationForm() {
    const [formData, setFormData] = useState({
        date: '',
        time: '',
        price: 12.00,
        type: '', 
        patientName: '', 
        doctorName: '', 
        hospitalName: ''
    });

    const handleChange = (e) => {
        setFormData({...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        console.log(formData);
        axios.post('http://localhost:8080/api/appointments', {
            date: formData.date,
            time: formData.time,
            price: formData.price,
            type: formData.type,
            patientName: formData.patientName,
            doctorName: formData.doctorName,
            hospitalName: formData.hospitalName
        })
            .then(response => {
                alert('Consultation added successfully');
                setFormData({ number: '', name: '', date: '', time: '', doctorId: '', specialty: '', hospitalId: '' });
            })
            .catch(error => {
                console.error('Error adding consultation', error);
                alert('Failed to add consultation');
            });
    };

    return (
        <div className="form-consultation-container">
            <form onSubmit={handleSubmit} className="form-container">
                <div className="form-row">
                    {/* <input type="text" name="number" value={formData.number} onChange={handleChange} placeholder="User Number" required /> */}
                    <input type="text" name="patientName" value={formData.patientName} onChange={handleChange} placeholder="User Name" required />
                </div>
                <div className="form-row">
                    <input type="date" name="date" value={formData.date} onChange={handleChange} required />
                    <input type="time" name="time" value={formData.time} onChange={handleChange} required />
                </div>
                <div className="form-row">
                    <input type="text" name="doctorName" value={formData.doctorName} onChange={handleChange} placeholder="Doctor ID" required />
                    <input type="text" name="type" value={formData.type} onChange={handleChange} placeholder="Specialty" required />
                </div>
                <div className="form-row">
                    <input type="text" name="hospitalName" value={formData.hospitalName} onChange={handleChange} placeholder="Hospital Name" required />
                </div>
                <button className="add-consultation-btn" type="submit">Add Consultation</button>
            </form>
        </div>
    );
}

export default AddConsultationForm;