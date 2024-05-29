import React, { useState, useEffect } from 'react';
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
                console.log('Response:', response); 
                const newAppointment = response.data;
                console.log('Detalhes da nova consulta:', newAppointment);
                setFormData({ date: '', time: '', price: 12.00, type: '', patientName: '', doctorName: '', hospitalName: '' });

            })
            .catch(error => {
                console.error('Error adding consultation', error);
                alert('Failed to add consultation');
            });
    };

    const [hospitals, setHospitals] = useState([]);

    const fetchHospitals = async () => {
        try {
            const response = await axios.get('http://localhost:8080/api/hospitals/');
            setHospitals(response.data);
        } catch (error) {
            console.error('Erro ao buscar hospitais', error);
        }
    };

    const [doctors, setDoctors] = useState([]);

    const fetchDoctors = async () => {
        try {
            const response = await axios.get('http://localhost:8080/api/doctors/');
            setDoctors(response.data); 
        } catch (error) {
            console.error('Erro ao buscar médicos', error);
        }
    };

    useEffect(() => {
        fetchHospitals(); 
        fetchDoctors(); 
    }, []);

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
                    <select name="doctorName" value={formData.doctorName} onChange={handleChange}>
                        <option value="">Select a Doctor</option>
                        {doctors.map((doctor) => (
                            <option key={doctor.id} value={doctor.name}>{doctor.name}</option>
                        ))}
                    </select>
                    <input type="text" name="type" value={formData.type} onChange={handleChange} placeholder="Specialty" required />
                </div>
                <div className="form-row">
                    <select name="hospitalName" value={formData.hospitalName} onChange={handleChange}>
                        <option value="">Select a Hospital</option>
                        {hospitals.map((hospital) => (
                            <option key={hospital.id} value={hospital.name}>{hospital.name}</option>
                        ))}
                    </select>
                </div>
                <button className="add-consultation-btn" type="submit">Add Consultation</button>
            </form>
        </div>
    );
}

export default AddConsultationForm;