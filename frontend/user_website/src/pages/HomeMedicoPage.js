import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import './HomePage.css';

import MedicoCard from '../components/MedicoCard';

const HomeMedicoPage = () => {

    const hospitals = [
        {
            image: 'https://static.vecteezy.com/system/resources/previews/008/957/225/non_2x/female-doctor-avatar-clipart-icon-in-flat-design-vector.jpg',
            name: 'Hospital Lusíadas Santa Maria da Feira ',
            city: 'SANTA MARIA DA FEIRA',
            number: '226 085 580',
            location: 'Localização',
        },
        {
            image: 'https://st4.depositphotos.com/7877830/25337/v/450/depositphotos_253374286-stock-illustration-vector-illustration-male-doctor-avatar.jpg',
            name: 'Hospital Lusíadas Braga ',
            city: 'BRAGA',
            number: '226 085 580',
            location: 'Localização',
        },
        {
            image: 'https://st4.depositphotos.com/7877830/25337/v/450/depositphotos_253374286-stock-illustration-vector-illustration-male-doctor-avatar.jpg',
            name: 'Nome do Hospital 1',
            city: 'CIDADE',
            number: '226 085 580',
            location: 'Localização',
        },
        {
            image: 'https://static.vecteezy.com/system/resources/previews/008/957/225/non_2x/female-doctor-avatar-clipart-icon-in-flat-design-vector.jpg',
            name: 'Nome do Hospital 2',
            city: 'CIDADE',
            number: '226 085 580',
            location: 'Localização',
        },
        {
            image: 'https://static.vecteezy.com/system/resources/previews/008/957/225/non_2x/female-doctor-avatar-clipart-icon-in-flat-design-vector.jpg',
            name: 'Nome do Hospital 1',
            city: 'CIDADE',
            number: '226 085 580',
            location: 'Localização',
        },
        {
            image: 'https://st4.depositphotos.com/7877830/25337/v/450/depositphotos_253374286-stock-illustration-vector-illustration-male-doctor-avatar.jpg',
            name: 'Nome do Hospital 2',
            city: 'CIDADE',
            number: '226 085 580',
            location: 'Localização',
        },
    ];

    return (
        <div className="home-page">
            <div className="overlay-hp overlay-medico">
                <img src="https://media.npr.org/assets/img/2021/02/19/scott_kobner03_wide-fd05f3d15fbb710e3b0d1f63940134616c2987a6.jpg?s=1400&c=100&f=jpeg" alt="Imagem de Fundo" className="background-image-hp" />
            </div>

            <Link to="/hospitais"><button className="homepage-button hp-hospital-button">Hospitais</button></Link>
            <Link to="/medicos"><button className="homepage-button hp-medico-button hp-medico-button-active">Médicos</button></Link>
            <Link to="/especialidades"><button className="homepage-button hp-especialidade-button">Especialidades</button></Link>

            <div className="hospital-cards-container">
                {hospitals.map((hospital, index) => (
                    <MedicoCard
                        key={index}
                        image={hospital.image}
                        name={hospital.name}
                        city={hospital.city}
                        number={hospital.number}
                        location={hospital.location}
                    />
                ))}
            </div>
        </div>
    );
};

export default HomeMedicoPage;
