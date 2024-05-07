package pt.ua.deti.tqs.cliniconnect.models;

import jakarta.persistence.*;
import java.util.Set;
import lombok.*;


@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Patient extends Persona {
    private String preferredHospital;

    @OneToMany(mappedBy = "patient")
    private Set<Appointment> appointments;
}
