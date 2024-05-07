package pt.ua.deti.tqs.cliniconnect.services;

import pt.ua.deti.tqs.cliniconnect.models.Doctor;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface DoctorService {
    List<Doctor> getDoctorsBySpeciality(String speciality);
}