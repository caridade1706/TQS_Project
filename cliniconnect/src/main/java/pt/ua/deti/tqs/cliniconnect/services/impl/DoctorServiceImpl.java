package pt.ua.deti.tqs.cliniconnect.services.impl;

import pt.ua.deti.tqs.cliniconnect.Roles;
import pt.ua.deti.tqs.cliniconnect.dto.AddDoctorDTO;
import pt.ua.deti.tqs.cliniconnect.models.Appointment;
import pt.ua.deti.tqs.cliniconnect.models.Doctor;
import pt.ua.deti.tqs.cliniconnect.models.Hospital;
import pt.ua.deti.tqs.cliniconnect.repositories.DoctorRepository;
import pt.ua.deti.tqs.cliniconnect.repositories.HospitalRepository;
import pt.ua.deti.tqs.cliniconnect.services.DoctorService;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.print.Doc;

import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private DoctorRepository doctorRepository;
    private HospitalRepository hospitalRepository;

    @Override
    public Doctor addDoctor(AddDoctorDTO addDoctorDTO) {

        Set<Hospital> hospitals = new HashSet<>();
        Set<Appointment> appointments = new HashSet<>();


        Optional<Hospital> hospital = hospitalRepository.findByName(addDoctorDTO.getHospital());
        
        if (hospital.isPresent()) {
            hospitals.add(hospital.get());
        } else {
            return null;
        }

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
        doctor.setHospitals(hospitals);
        doctor.setAppointments(appointments);
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