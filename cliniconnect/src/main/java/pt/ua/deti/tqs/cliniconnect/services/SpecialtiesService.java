package pt.ua.deti.tqs.cliniconnect.services;

import org.springframework.stereotype.Service;
import pt.ua.deti.tqs.cliniconnect.models.Specialties;
import pt.ua.deti.tqs.cliniconnect.models.Patient;

import java.util.HashMap;

@Service
public interface SpecialtiesService {
    HashMap<String, Integer> getByPatient(Patient patient);
    Specialties addSpecialty(Patient patient, HashMap<String, Integer> specialties);
    Specialties deleteSpecialty(Patient patient, String specialty);
}