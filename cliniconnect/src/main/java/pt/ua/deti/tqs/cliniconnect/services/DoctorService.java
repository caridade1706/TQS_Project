package pt.ua.deti.tqs.cliniconnect.services;

import pt.ua.deti.tqs.cliniconnect.dto.AddDoctorDTO;
import pt.ua.deti.tqs.cliniconnect.models.Doctor;
import java.util.List;

public interface DoctorService {
    List<Doctor> getDoctorsBySpeciality(String speciality);
    Doctor addDoctor(AddDoctorDTO addDoctorDTO);
}