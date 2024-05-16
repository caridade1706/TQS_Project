package pt.ua.deti.tqs.cliniconnect.models;

import jakarta.persistence.*;
import java.util.Set;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Staff extends Persona {
    private String department;
    private String task;

    @ManyToMany(mappedBy = "staffs")
    @Transient
    private Set<Hospital> hospitals;

    @Override
    public String getUsername() {
        return super.getEmail();
    }
}