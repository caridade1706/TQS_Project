package pt.ua.deti.tqs.cliniconnect.repositories;

import pt.ua.deti.tqs.cliniconnect.models.Staff;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRepository extends JpaRepository<Staff, UUID> {
    Staff findByEmail(String email);

}