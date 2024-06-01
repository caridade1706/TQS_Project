package pt.ua.deti.tqs.cliniconnect.repositories;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pt.ua.deti.tqs.cliniconnect.models.Appointment;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {
    List<Appointment> findByDoctor_IdAndDate(UUID doctorId, Date date);
    List<Appointment> findByDate(Date date);
    List<Appointment> findByPatient_Id(UUID patientId);
    List<Appointment> findByPatient_IdAndDateAfter(UUID patientId, Date date);
    List<Appointment> findByPatient_IdAndDateBefore(UUID patientId, Date date);

    @Query("SELECT a FROM Appointment a WHERE a.patient.id = :userId AND a.date < :currentDate")
    List<Appointment> findPastAppointmentsByUserIdAndDateBefore(@Param("userId") UUID userId, @Param("currentDate") Instant currentDate);
}