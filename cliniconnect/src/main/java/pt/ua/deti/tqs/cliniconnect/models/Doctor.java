package pt.ua.deti.tqs.cliniconnect.models;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import lombok.*;
import pt.ua.deti.tqs.cliniconnect.Roles;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Doctor extends Persona {
    private String speciality;

    @ManyToMany(mappedBy = "doctors")
    private Set<Hospital> hospitals;

    @OneToMany(mappedBy = "doctor")
    private Set<Appointment> appointments;

    public Doctor(UUID id, String name, Date dob, String email, String password, String phone, String address, String city, String speciality, Set<Hospital> hospitals, Set<Appointment> appointments) {
        super(id, name, dob, email, password, phone, address, city, Roles.DOCTOR);
        this.speciality = speciality;
        this.hospitals = hospitals;
        this.appointments = appointments;
    }

    @Override
    public String getUsername() {
        return super.getEmail();
    }
}
