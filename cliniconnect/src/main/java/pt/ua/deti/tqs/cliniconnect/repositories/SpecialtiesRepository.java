package pt.ua.deti.tqs.cliniconnect.repositories;

import java.util.HashMap;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pt.ua.deti.tqs.cliniconnect.models.Patient;

import pt.ua.deti.tqs.cliniconnect.models.Specialties;

@Repository
public interface SpecialtiesRepository extends JpaRepository<Specialties, Long> {   

    HashMap<String, Integer> findByPatient(Patient patient);
    
}