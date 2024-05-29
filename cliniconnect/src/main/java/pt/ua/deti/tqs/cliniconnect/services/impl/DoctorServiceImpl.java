package pt.ua.deti.tqs.cliniconnect.services.impl;

import pt.ua.deti.tqs.cliniconnect.Roles;
import pt.ua.deti.tqs.cliniconnect.dto.AddDoctorDTO;
import pt.ua.deti.tqs.cliniconnect.models.Doctor;
import pt.ua.deti.tqs.cliniconnect.models.Hospital;
import pt.ua.deti.tqs.cliniconnect.repositories.DoctorRepository;
import pt.ua.deti.tqs.cliniconnect.services.DoctorService;

import java.util.List;

import javax.print.Doc;

import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private DoctorRepository doctorRepository;

    @Override
    public Doctor addDoctor(AddDoctorDTO addDoctorDTO) {
        Doctor doctor = new Doctor();
        doctor.setName(addDoctorDTO.getName());
        doctor.setDob(addDoctorDTO.getDob());
        doctor.setEmail(addDoctorDTO.getEmail());
        doctor.setPassword(null);
        doctor.setPhone(addDoctorDTO.getPhone());
        doctor.setAddress(addDoctorDTO.getAddress());
        doctor.setCity(addDoctorDTO.getCity());
        doctor.setSpeciality(addDoctorDTO.getSpeciality());
        doctor.setRole(Roles.DOCTOR);
        doctorRepository.save(doctor);
        return doctor;
    }
    
    @Override
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    @Override
    public List<Doctor> getDoctorsBySpeciality(String speciality) {
        return doctorRepository.findBySpeciality(speciality);
    }
}