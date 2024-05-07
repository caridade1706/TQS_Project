package pt.ua.deti.tqs.cliniconnect.repositories;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pt.ua.deti.tqs.cliniconnect.models.Appointment;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {
    List<Appointment> findByPatient_Id(UUID patientId);
    List<Appointment> findByDoctor_IdAndDate(UUID doctorId, Date date);
}