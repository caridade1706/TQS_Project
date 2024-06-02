package pt.ua.deti.tqs.cliniconnect.models;

import jakarta.persistence.*;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Patient extends Persona {
    
    private String patientNumber;
    private String preferredHospital;

    @OneToMany(mappedBy = "patient")
    @JsonBackReference
    private Set<Appointment> appointments;

    public Patient(UUID id, String name, Date dob, String email, String password, String phone, String address,
            String city, String patientNumber, String preferredHospital, Set<Appointment> appointments) {
        super(id, name, dob, email, password, phone, address, city, Roles.PATIENT);
        this.patientNumber = patientNumber;
        this.preferredHospital = preferredHospital;
        this.appointments = appointments;
    }

    @Override
    public String getUsername() {
        return super.getEmail();
    }
}
