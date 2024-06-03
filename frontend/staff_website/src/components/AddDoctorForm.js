import React, { useState, useEffect } from 'react';
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
        console.log('Doctor data:', doctorData);
        axios.post(process.env.REACT_APP_API_URL + '/doctors/', doctorData)
            .then(response => {
                alert('Doctor added successfully!');
                console.log(response.data);
                setDoctorData({ name: '', address: '', city: '', speciality: '', hospital: '' }); // Reset form after successful submission
            })
            .catch(error => {
                console.error('Error adding doctor:', error);
                alert('Failed to add doctor');
            });
    };

    const [hospitals, setHospitals] = useState([]);
    useEffect(() => {
        fetchHospitals();
    }, []);
    
    const fetchHospitals = async () => {
        try {
            const response = await axios.get(process.env.REACT_APP_API_URL + 'hospitals/');
            setHospitals(response.data);
        } catch (error) {
            console.error('Error fetching hospitals:', error);
        }
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
                <select name="hospital" value={doctorData.hospital} onChange={handleChange} placeholder="Hospital" style={{height: "40px", marginTop: "10px"}} required>
                    <option value="">Hospital</option>
                    {hospitals.map(hospital => (
                        <option key={hospital.id} value={hospital.name}>{hospital.name}</option>
                    ))}
                </select>

                <button className="add-doctor-btn" type="submit">Add Doctor</button>
            </form>
        </div>
    );
}

export default AddDoctorForm;