package pt.ua.deti.tqs.cliniconnect.repositories;

import pt.ua.deti.tqs.cliniconnect.models.QueueManagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QueueManagementRepository extends JpaRepository<QueueManagement, Long> {
    // Add custom queries here
}