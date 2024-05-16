package pt.ua.deti.tqs.cliniconnect.repositories;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pt.ua.deti.tqs.cliniconnect.models.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {
    Patient findByEmail(String email);
}