package pt.ua.deti.tqs.cliniconnect.services;

import pt.ua.deti.tqs.cliniconnect.models.Patient;

public interface PatientService {
    Patient getPatientByEmail(String email);
}