import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { Link } from 'react-router-dom';

function Register() {

  const navigate = useNavigate()
  const [name, setName] = useState('');
  const [dob, setDob] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [phone, setPhone] = useState('');
  const [address, setAddress] = useState('');
  const [city, setCity] = useState('');
  const [preferredHospital, setPreferredHospital] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post('http://localhost:8080/api/patients/register', {
        name,
        dob,
        email,
        password,
        phone,
        address,
        city,
        preferredHospital
      });
      console.log(response.data);
      alert('Registration successful');
      alert(JSON.stringify(response.data));
      // navigate('/'); 
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <div className="App" style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', justifyContent: 'center', minHeight: '100vh', padding: '20px', boxSizing: 'border-box' }}>
      <h1 style={{ fontSize: '2em', color: '#007bff', marginBottom: '20px' }}>Register</h1>
      <form onSubmit={handleSubmit} style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', gap: '10px', marginBottom: '20px' }}>
        <input type="text" name="Name" value={name} onChange={(e) => setName(e.target.value)} placeholder="Name" style={{ width: '100%', padding: '10px', margin: '10px 0', boxSizing: 'border-box', border: '1px solid #ccc', borderRadius: '4px' }} required />
        <input type="date" name="Date of Birth" value={dob} onChange={(e) => setDob(e.target.value)} placeholder="Date of Birth" style={{ width: '100%', padding: '10px', margin: '10px 0', boxSizing: 'border-box', border: '1px solid #ccc', borderRadius: '4px' }} required />
        <input type="email" name="email" value={email} onChange={(e) => setEmail(e.target.value)} placeholder="Email" style={{ width: '100%', padding: '10px', margin: '10px 0', boxSizing: 'border-box', border: '1px solid #ccc', borderRadius: '4px' }} required />
        <input type="password" name="password" value={password} onChange={(e) => setPassword(e.target.value)} placeholder="Password" style={{ width: '100%', padding: '10px', margin: '10px 0', boxSizing: 'border-box', border: '1px solid #ccc', borderRadius: '4px' }} required />
        <input type="text" name="phone" value={phone} onChange={(e) => setPhone(e.target.value)} placeholder="Phone" style={{ width: '100%', padding: '10px', margin: '10px 0', boxSizing: 'border-box', border: '1px solid #ccc', borderRadius: '4px' }} required />
        <input type="text" name="address" value={address} onChange={(e) => setAddress(e.target.value)} placeholder="Address" style={{ width: '100%', padding: '10px', margin: '10px 0', boxSizing: 'border-box', border: '1px solid #ccc', borderRadius: '4px' }} required />
        <input type="text" name="city" value={city} onChange={(e) => setCity(e.target.value)} placeholder="City" style={{ width: '100%', padding: '10px', margin: '10px 0', boxSizing: 'border-box', border: '1px solid #ccc', borderRadius: '4px' }} required />
        <input type="text" name="preferredHospital" value={preferredHospital} onChange={(e) => setPreferredHospital(e.target.value)} placeholder="Preferred Hospital" style={{ width: '100%', padding: '10px', margin: '10px 0', boxSizing: 'border-box', border: '1px solid #ccc', borderRadius: '4px' }} required />
        
        
        
        <button type="submit" style={{ padding: '10px 20px', margin: '10px 0', cursor: 'pointer', border: 'none', borderRadius: '4px', backgroundColor: '#007bff', color: 'white' }}>Register</button>
      
      
      
      
      
      </form>
      
      
      
      <div style={{ textAlign: 'center', marginTop: '20px' }}>
        <Link to="/login" style={{ color: '#007bff', textDecoration: 'none' }}>Already have an account? Login</Link>
      </div>
    </div>
  );
}

export default Register;