package pt.ua.deti.tqs.cliniconnect.repositories;

import pt.ua.deti.tqs.cliniconnect.models.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface PersonaRepository extends JpaRepository<Persona, UUID> {
    Optional<Persona> findByEmail(String email);
    Persona findByName(String name);
}