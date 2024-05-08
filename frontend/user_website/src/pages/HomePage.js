import React, { useState } from 'react';
import './HomePage.css';

import HospitalCard from '../components/HospitalCard';

const HomePage = () => {

    const hospitals = [
        {
            image: 'https://www.trofasaude.pt/wp-content/uploads/2022/05/hospital.jpg',
            name: 'Hospital Lusíadas Santa Maria da Feira ',
            city: 'SANTA MARIA DA FEIRA',
            number: '226 085 580',
            location: 'Localização 1',
        },
        {
            image: 'https://www.trofasaude.pt/wp-content/uploads/2022/05/hospital.jpg',
            name: 'Hospital Lusíadas Braga ',
            city: 'BRAGA',
            number: 'Número 2',
            location: 'Localização 2',
        },
        {
            image: 'https://www.trofasaude.pt/wp-content/uploads/2022/05/hospital.jpg',
            name: 'Nome do Hospital 1',
            city: 'Cidade 1',
            number: 'Número 1',
            location: 'Localização 1',
        },
        {
            image: 'https://www.trofasaude.pt/wp-content/uploads/2022/05/hospital.jpg',
            name: 'Nome do Hospital 2',
            city: 'Cidade 2',
            number: 'Número 2',
            location: 'Localização 2',
        },
        {
            image: 'https://www.trofasaude.pt/wp-content/uploads/2022/05/hospital.jpg',
            name: 'Nome do Hospital 1',
            city: 'Cidade 1',
            number: 'Número 1',
            location: 'Localização 1',
        },
        {
            image: 'https://www.trofasaude.pt/wp-content/uploads/2022/05/hospital.jpg',
            name: 'Nome do Hospital 2',
            city: 'Cidade 2',
            number: 'Número 2',
            location: 'Localização 2',
        },
    ];

    return (
        <div className="home-page">
            <img src="https://media.npr.org/assets/img/2021/02/19/scott_kobner03_wide-fd05f3d15fbb710e3b0d1f63940134616c2987a6.jpg?s=1400&c=100&f=jpeg" alt="Imagem de Fundo" className="background-image" />

            <button className="homepage-button hp-hospital-button">Hospitais</button>
            <button className="homepage-button hp-medico-button">Médicos</button>
            <button className="homepage-button hp-especialidade-button">Especialidades</button>

            <div className="hospital-cards-container"> {/* Adicionado um contêiner para os cards */}
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

export default HomePage;
