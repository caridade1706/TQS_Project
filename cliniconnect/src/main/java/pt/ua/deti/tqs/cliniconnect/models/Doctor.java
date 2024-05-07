package pt.ua.deti.tqs.cliniconnect.models;

import jakarta.persistence.*;
import java.util.Set;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Doctor extends Persona {
    private String speciality;

    @ManyToMany(mappedBy = "doctors")
    private Set<Hospital> hospitals;

    @OneToMany(mappedBy = "doctor")
    private Set<Appointment> appointments;
}