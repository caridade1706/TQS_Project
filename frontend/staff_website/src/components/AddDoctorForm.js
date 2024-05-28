import React, { useState } from 'react';
import axios from 'axios';
import './AddDoctorForm.css';

function AddDoctorForm() {
    const [doctorData, setDoctorData] = useState({
        name: '', dob: '', email: '', phone: '', dob: '', email: '', speciality: '', hospital: ''
    });

    const handleChange = (e) => {
        setDoctorData({...doctorData, [e.target.name]: e.target.value });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        axios.post('http://localhost:8080/api/doctors/', doctorData)
            .then(response => {
                alert('Doctor added successfully!');
                console.log(response.data);
                setDoctorData({ name: '', address: '', city: '' }); // Reset form after successful submission
            })
            .catch(error => {
                console.error('Error adding doctor:', error);
                alert('Failed to add doctor');
            });
    };

    return (
        <div className="form-doctor-container">
            <form onSubmit={handleSubmit} className="form-d-container">
                <input type="text" name="name" value={doctorData.name} onChange={handleChange} placeholder="Doctor Name" required />
                <input type="date" name="dob" value={doctorData.dob} onChange={handleChange} placeholder="Date of Birth" required />
                <input type="email" name="email" value={doctorData.email} onChange={handleChange} placeholder="Email" required />
                <input type="text" name="phone" value={doctorData.phone} onChange={handleChange} placeholder="Phone" required />
                <input type="text" name="address" value={doctorData.address} onChange={handleChange} placeholder="Address" required />
                <input type="text" name="city" value={doctorData.city} onChange={handleChange} placeholder="City" required />
                <input type="text" name="speciality" value={doctorData.speciality} onChange={handleChange} placeholder="Speciality" required />
                <input type="text" name="hospital" value={doctorData.hospital} onChange={handleChange} placeholder="Hospital" required />
                <button className="add-doctor-btn" type="submit">Add Doctor</button>
            </form>
        </div>
    );
}

export default AddDoctorForm;