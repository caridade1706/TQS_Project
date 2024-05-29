package pt.ua.deti.tqs.cliniconnect.models;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.*;
import pt.ua.deti.tqs.cliniconnect.Roles;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Patient extends Persona {
    private String preferredHospital;

    // @OneToMany(mappedBy = "patient")
    // @JsonManagedReference
    // private Set<Appointment> appointments;

    @OneToMany(mappedBy = "patient")
    @JsonBackReference
    private Set<Appointment> appointments;

    public Patient(UUID id, String name, Date dob, String email, String password, String phone, String address,
            String city, String preferredHospital, Set<Appointment> appointments) {
        super(id, name, dob, email, password, phone, address, city, Roles.PATIENT);
        this.preferredHospital = preferredHospital;
        this.appointments = appointments;
    }

    @Override
    public String getUsername() {
        return super.getEmail();
    }
}