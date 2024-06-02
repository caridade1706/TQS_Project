package pt.ua.deti.tqs.cliniconnect.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pt.ua.deti.tqs.cliniconnect.models.Patient;

import pt.ua.deti.tqs.cliniconnect.models.Specialties;

@Repository
public interface SpecialtiesRepository extends JpaRepository<Specialties, Long> {   
    Specialties findByPatient(Patient patient);
}