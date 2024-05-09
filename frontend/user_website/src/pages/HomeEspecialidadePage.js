import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import './HomePage.css';

import EspecialidadeCard from '../components/EspecialidadeCard';

const HomeEspecialidadePage = () => {

    const hospitals = [
        {
            image: 'https://static.vecteezy.com/system/resources/thumbnails/011/718/509/small_2x/brain-logo-template-design-brainstorm-logo-ideas-neurology-logo-think-idea-concept-free-vector.jpg',
            name: 'Hospital Lusíadas Santa Maria da Feira ',
            city: 'SANTA MARIA DA FEIRA',
            number: '226 085 580',
            location: 'Localização',
        },
        {
            image: 'https://www.creativefabrica.com/wp-content/uploads/2022/02/23/Ear-hearing-otology-icon-Graphics-25858887-1.jpg',
            name: 'Hospital Lusíadas Braga ',
            city: 'BRAGA',
            number: '226 085 580',
            location: 'Localização',
        },
        {
            image: 'https://static.vecteezy.com/system/resources/previews/006/397/867/original/eye-insurance-icon-eye-care-icon-optometry-icon-free-vector.jpg',
            name: 'Nome do Hospital 1',
            city: 'CIDADE',
            number: '226 085 580',
            location: 'Localização',
        },
        {
            image: 'https://thumbs.dreamstime.com/b/anatomia-humana-do-cora%C3%A7%C3%A3o-72820305.jpg',
            name: 'Nome do Hospital 2',
            city: 'CIDADE',
            number: '226 085 580',
            location: 'Localização',
        },
        {
            image: 'https://img.freepik.com/premium-vector/lungs-icon-clipart-avatar-logotype-isolated-vector-illustration_955346-66.jpg',
            name: 'Nome do Hospital 1',
            city: 'CIDADE',
            number: '226 085 580',
            location: 'Localização',
        },
        {
            image: 'https://t3.ftcdn.net/jpg/00/80/59/96/360_F_80599666_qaCh2HsJaZGqUCN1m2Uu1CrgRdByxAue.jpg',
            name: 'Nome do Hospital 2',
            city: 'CIDADE',
            number: '226 085 580',
            location: 'Localização',
        },
    ];

    return (
        <div className="home-page">
            <div className="overlay-hp overlay-especialidade">
                <img src="https://media.npr.org/assets/img/2021/02/19/scott_kobner03_wide-fd05f3d15fbb710e3b0d1f63940134616c2987a6.jpg?s=1400&c=100&f=jpeg" alt="Imagem de Fundo" className="background-image-hp" />
            </div>

            <Link to="/hospitais"><button className="homepage-button hp-hospital-button">Hospitais</button></Link>
            <Link to="/medicos"><button className="homepage-button hp-medico-button">Médicos</button></Link>
            <Link to="/especialidades"><button className="homepage-button hp-especialidade-button hp-especialidade-button-active">Especialidades</button></Link>

            <div className="hospital-cards-container">
                {hospitals.map((hospital, index) => (
                    <EspecialidadeCard
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

export default HomeEspecialidadePage;
