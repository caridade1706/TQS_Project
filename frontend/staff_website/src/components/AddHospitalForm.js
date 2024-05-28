import React, { useState } from 'react';
import axios from 'axios';
import './AddHospitalForm.css';

function AddHospitalForm() {
    const [hospitalData, setHospitalData] = useState({
        name: '', address: '', city: ''
    });

    const handleChange = (e) => {
        setHospitalData({...hospitalData, [e.target.name]: e.target.value });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        axios.post('http://localhost:8080/api/hospitals/', hospitalData)
            .then(response => {
                alert('Hospital added successfully!');
                console.log(response.data);
                setHospitalData({ name: '', address: '', city: '' }); // Reset form after successful submission
            })
            .catch(error => {
                console.error('Error adding hospital:', error);
                alert('Failed to add hospital');
            });
    };

    return (
        <div className="form-hospital-container">
            <form onSubmit={handleSubmit} className="form-h-container">
                <input type="text" name="name" value={hospitalData.name} onChange={handleChange} placeholder="Hospital Name" required />
                <input type="text" name="address" value={hospitalData.address} onChange={handleChange} placeholder="Address" required />
                <input type="text" name="city" value={hospitalData.city} onChange={handleChange} placeholder="City" required />
                <button className="add-hospital-btn" type="submit">Add Hospital</button>
            </form>
        </div>
    );
}

export default AddHospitalForm;