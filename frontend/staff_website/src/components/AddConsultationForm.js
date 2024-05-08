import React, { useState } from 'react';

function AddConsultationForm({ addConsultation }) {
    const [formData, setFormData] = useState({
        number: '', name: '', date: '', time: '', doctor: '', specialty: '', hospital: ''
    });

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        addConsultation(formData);
        setFormData({ number: '', name: '', date: '', time: '', doctor: '', specialty: '', hospital: '' });
    };

    return (
        <form onSubmit={handleSubmit}>
            <input type="text" name="number" value={formData.number} onChange={handleChange} placeholder="User Number" required />
            <input type="text" name="name" value={formData.name} onChange={handleChange} placeholder="User Name" required />
            <input type="date" name="date" value={formData.date} onChange={handleChange} required />
            <input type="time" name="time" value={formData.time} onChange={handleChange} required />
            <input type="text" name="doctor" value={formData.doctor} onChange={handleChange} placeholder="Doctor" required />
            <input type="text" name="specialty" value={formData.specialty} onChange={handleChange} placeholder="Specialty" required />
            <input type="text" name="hospital" value={formData.hospital} onChange={handleChange} placeholder="Hospital" required />
            <button type="submit">Add Consultation</button>
        </form>
    );
}

export default AddConsultationForm;
