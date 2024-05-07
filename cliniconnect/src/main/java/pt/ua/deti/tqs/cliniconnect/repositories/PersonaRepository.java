package pt.ua.deti.tqs.cliniconnect.repositories;


import pt.ua.deti.tqs.cliniconnect.models.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, UUID> {
    
}