import { useState } from 'react'
import axios from 'axios'
import { useNavigate } from 'react-router-dom';
import { Link } from 'react-router-dom';

function Login(props) {

  const navigate = useNavigate()
  const [credentials, setCredentials] = useState({})

  const handleChange = (e) => {
    setCredentials({
      ...credentials,
      [e.target.name]: e.target.value
    })
  }

  const handleLogin = () => {
    // perform the login request 
  
    axios.post('http://localhost:8080/api/patients/login',{
      email: credentials.email, 
      password: credentials.password    
    }).then(response => {
      if(response.data) {     
        const token = response.data.token
        localStorage.setItem(credentials.email, token)
        navigate('/'); 
        props.history.push('/accounts')
        localStorage.setItem('email', credentials.email)
        props.onLoggedIn()   
      }
    }).catch(error => {
      console.log(error)    
    })

  }

  const handleLogOut = () => {
    localStorage.removeItem('access_token')
  }

  return (
    <div className="App" style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', justifyContent: 'center', minHeight: '100vh', padding: '20px', boxSizing: 'border-box' }}>
      <h1 style={{ fontSize: '2em', color: '#007bff', marginBottom: '20px' }}>Login</h1>
      <div style={{ display: 'flex', flexDirection: 'row', justifyContent: 'center', gap: '10px', marginBottom: '20px' }}>
        <input type="text" name="email" onChange={handleChange} placeholder="Enter email" style={{ width: '45%', padding: '10px', margin: '10px 0', boxSizing: 'border-box', border: '1px solid #ccc', borderRadius: '4px' }} />
        <input type="password" name="password" onChange={handleChange} placeholder="Enter password" style={{ width: '45%', padding: '10px', margin: '10px 0', boxSizing: 'border-box', border: '1px solid #ccc', borderRadius: '4px' }} />
      </div>
      <div style={{ display: 'flex', flexDirection: 'row', justifyContent: 'center', gap: '10px' }}>
        <button onClick={handleLogin} style={{ padding: '10px 20px', margin: '10px 0', cursor: 'pointer', border: 'none', borderRadius: '4px', backgroundColor: '#007bff', color: 'white' }}>Login</button>
        <button onClick={handleLogOut} style={{ padding: '10px 20px', margin: '10px 0', cursor: 'pointer', border: 'none', borderRadius: '4px', backgroundColor: '#dc3545', color: 'white' }}>Log Out</button>
      </div>
      <div style={{ textAlign: 'center', marginTop: '20px' }}>
        <Link to="/register" style={{ color: '#007bff', textDecoration: 'none' }}>Don't have an account? Sign Up</Link>
      </div>
    </div>
  );
}

export default Login;