import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './AddConsultationForm.css';

function AddConsultationForm({ onClose, onAddConsultation }) {
    const [formData, setFormData] = useState({
        date: '',
        time: '',
        price: 12.00,
        type: '', 
        patientName: '', 
        patientNumber: '', 
        doctorName: '', 
        hospitalName: ''
    });

    const [notification, setNotification] = useState(null);
    const [hospitals, setHospitals] = useState([]);
    const [doctors, setDoctors] = useState([]);
    const [patients, setPatients] = useState([]);
    const [filteredDoctors, setFilteredDoctors] = useState([]);

    useEffect(() => {
        fetchHospitals(); 
        fetchDoctors(); 
        fetchPatients();
    }, []);
    
    useEffect(() => {
        if (doctors.length > 0) {
            extractUniqueSpecialties();
        }
    }, [doctors]);
    

    const fetchHospitals = async () => {
        try {
            const response = await axios.get(process.env.REACT_APP_API_URL + 'hospitals/');
            setHospitals(response.data);
        } catch (error) {
            console.error('Erro ao buscar hospitais', error);
        }
    };

    const fetchDoctors = async () => {
        try {
            const response = await axios.get(process.env.REACT_APP_API_URL + 'doctors/');
            setDoctors(response.data);
            setFilteredDoctors(response.data); // Initialize filtered doctors
        } catch (error) {
            console.error('Erro ao buscar médicos', error);
        }
    };

    const fetchPatients = async () => {
        try {
            const response = await axios.get(process.env.REACT_APP_API_URL + 'patients/');
            setPatients(response.data);
        } catch (error) {
            console.error('Erro ao buscar patients', error);
        }
    };

    // Estado para armazenar as especialidades únicas
    const [uniqueSpecialties, setUniqueSpecialties] = useState([]);

    const extractUniqueSpecialties = () => {
        const specialties = doctors.map(doctor => doctor.speciality).filter((value, index, self) => self.indexOf(value) === index);
        console.log("Especialidades Únicas:", specialties);
        setUniqueSpecialties(specialties); // Define uniqueSpecialties como as especialidades únicas
    };

    
    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData(prevState => ({ ...prevState, [name]: value }));

        if (name === 'patientNumber') {
            if (value === '') {
                // Reset to show all patients
                setFormData(prevState => ({ ...prevState, patientName: '' }));
            } else {
                const selectedPatient = patients.find(patient => patient.patientNumber === value);
                if (selectedPatient) {
                    setFormData(prevState => ({ ...prevState, patientName: selectedPatient.name }));
                } else {
                    setFormData(prevState => ({ ...prevState, patientName: '' }));
                }
            }
        } else if (name === 'patientName') {
            const selectedPatient = patients.find(patient => patient.name === value);
            if (selectedPatient) {
                setFormData(prevState => ({ ...prevState, patientNumber: selectedPatient.patientNumber }));
            } else {
                setFormData(prevState => ({ ...prevState, patientNumber: '' }));
            }
        } else if (name === 'doctorName') {
            const selectedDoctor = doctors.find(doctor => doctor.name === value);
            if (selectedDoctor) {
                setFormData(prevState => ({ ...prevState, type: selectedDoctor.speciality }));
            }
        } else if (name === 'type') {
            if (value === '') {
                setFilteredDoctors(doctors); // Reset to show all doctors
            } else {
                setFilteredDoctors(doctors.filter(doctor => doctor.speciality === value));
            }
            setFormData(prevState => ({ ...prevState, doctorName: '' })); // Clear selected doctor if type changes
        }
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        axios.post(process.env.REACT_APP_API_URL + 'appointments', formData)
            .then(response => {
                setNotification('Consultation added successfully');
                const newAppointment = response.data;
                onAddConsultation(newAppointment);
                setFormData({ date: '', time: '', price: 12.00, type: '', patientName: '', patientNumber: '', doctorName: '', hospitalName: '' });
                setTimeout(() => {
                    setNotification(null);
                    onClose();
                }, 2000);
                window.location.reload();
            })
            .catch(error => {
                console.error('Error adding consultation', error);
                alert('Failed to add consultation');
            });
    };

    return (
        <div>
            <div className="close-btn-container">
                <button className="close-button" onClick={onClose}>✕</button>
            </div>
            <form onSubmit={handleSubmit} className="form-container">
                <div className="form-row">
                    <input 
                        type="text" 
                        name="patientNumber" 
                        value={formData.patientNumber} 
                        onChange={handleChange} 
                        placeholder="Patient Number" 
                        required 
                    />
                    <select 
                        name="patientName" 
                        value={formData.patientName} 
                        onChange={handleChange}
                        required
                    >
                        <option value="">Select a Patient</option>
                        {patients.map((patient) => (
                            <option key={patient.id} value={patient.name}>{patient.name}</option>
                        ))}
                    </select>
                </div>
                <div className="form-row">
                    <input type="date" name="date" value={formData.date} onChange={handleChange} required />
                    <input type="time" name="time" value={formData.time} onChange={handleChange} required />
                </div>
                <div className="form-row">
                    <select name="doctorName" value={formData.doctorName} onChange={handleChange} required>
                        <option value="">Select a Doctor</option>
                        {filteredDoctors.map((doctor) => (
                            <option key={doctor.id} value={doctor.name}>{doctor.name}</option>
                        ))}
                    </select>
                    <select name="type" value={formData.type} onChange={handleChange} required>
                        <option value="">Select a Specialty</option>
                        {uniqueSpecialties.map((specialty, index) => (
                            <option key={index} value={specialty}>{specialty}</option>
                        ))}
                    </select>
                </div>
                <div className="form-row">
                    <select name="hospitalName" value={formData.hospitalName} onChange={handleChange} required>
                        <option value="">Select a Hospital</option>
                        {hospitals.map((hospital) => (
                            <option key={hospital.id} value={hospital.name}>{hospital.name}</option>
                        ))}
                    </select>
                </div>
                <button className="add-consultation-btn" type="submit">Add Consultation</button>
            </form>
            {notification && (
                <div className="notification">
                    {notification}
                </div>
            )}
        </div>
    );
}

export default AddConsultationForm;
