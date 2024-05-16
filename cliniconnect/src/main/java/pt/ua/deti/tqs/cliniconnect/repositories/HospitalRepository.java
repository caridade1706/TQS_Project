package pt.ua.deti.tqs.cliniconnect.repositories;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pt.ua.deti.tqs.cliniconnect.models.Hospital;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital, UUID> {
    // Add custom queries here
}