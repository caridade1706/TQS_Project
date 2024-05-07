package pt.ua.deti.tqs.cliniconnect.models;

import jakarta.persistence.*;
import java.util.Set;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Staff extends Persona {
    private String department;
    private String role;

    @ManyToMany(mappedBy = "staffs")
    private Set<Hospital> hospitals;
}
