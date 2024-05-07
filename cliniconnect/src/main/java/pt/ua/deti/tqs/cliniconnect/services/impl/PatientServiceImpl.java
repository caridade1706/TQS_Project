package pt.ua.deti.tqs.cliniconnect.services.impl;

import pt.ua.deti.tqs.cliniconnect.models.Patient;
import pt.ua.deti.tqs.cliniconnect.repositories.PatientRepository;
import pt.ua.deti.tqs.cliniconnect.services.PatientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PatientServiceImpl implements PatientService  {

    @Autowired
    private PatientRepository patientRepository;

    @Override
    public Patient getPatientByEmail(String email) {
        return patientRepository.findByEmail(email);
    }
}