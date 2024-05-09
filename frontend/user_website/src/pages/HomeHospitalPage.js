import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import './HomePage.css';

import HospitalCard from '../components/HospitalCard';

const HomeHospitalPage = () => {

    const hospitals = [
        {
            image: 'https://www.trofasaude.pt/wp-content/uploads/2022/05/hospital.jpg',
            name: 'Hospital Lusíadas Santa Maria da Feira ',
            city: 'SANTA MARIA DA FEIRA',
            number: '226 085 580',
            location: 'Localização',
        },
        {
            image: 'https://www.trofasaude.pt/wp-content/uploads/2022/05/hospital.jpg',
            name: 'Hospital Lusíadas Braga ',
            city: 'BRAGA',
            number: '226 085 580',
            location: 'Localização',
        },
        {
            image: 'https://www.trofasaude.pt/wp-content/uploads/2022/05/hospital.jpg',
            name: 'Nome do Hospital 1',
            city: 'CIDADE',
            number: '226 085 580',
            location: 'Localização',
        },
        {
            image: 'https://www.trofasaude.pt/wp-content/uploads/2022/05/hospital.jpg',
            name: 'Nome do Hospital 2',
            city: 'CIDADE',
            number: '226 085 580',
            location: 'Localização',
        },
        {
            image: 'https://www.trofasaude.pt/wp-content/uploads/2022/05/hospital.jpg',
            name: 'Nome do Hospital 1',
            city: 'CIDADE',
            number: '226 085 580',
            location: 'Localização',
        },
        {
            image: 'https://www.trofasaude.pt/wp-content/uploads/2022/05/hospital.jpg',
            name: 'Nome do Hospital 2',
            city: 'CIDADE',
            number: '226 085 580',
            location: 'Localização',
        },
    ];

    return (
        <div className="home-page">
            <div className="overlay-hp overlay-hospital">
                <img src="https://media.npr.org/assets/img/2021/02/19/scott_kobner03_wide-fd05f3d15fbb710e3b0d1f63940134616c2987a6.jpg?s=1400&c=100&f=jpeg" alt="Imagem de Fundo" className="background-image-hp" />
            </div>

            <Link to="/hospitais"><button className="homepage-button hp-hospital-button hp-hospital-button-active">Hospitais</button></Link>
            <Link to="/medicos"><button className="homepage-button hp-medico-button">Médicos</button></Link>
            <Link to="/especialidades"><button className="homepage-button hp-especialidade-button">Especialidades</button></Link>

            <div className="hospital-cards-container">
                {hospitals.map((hospital, index) => (
                    <HospitalCard
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

export default HomeHospitalPage;
