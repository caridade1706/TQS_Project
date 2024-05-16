package pt.ua.deti.tqs.cliniconnect.models;

import jakarta.persistence.*;
import java.util.Set;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Patient extends Persona {
    private String preferredHospital;

    @OneToMany(mappedBy = "patient")
    private Set<Appointment> appointments;

    @Override
    public String getUsername() {
        return super.getEmail();
    }
}
