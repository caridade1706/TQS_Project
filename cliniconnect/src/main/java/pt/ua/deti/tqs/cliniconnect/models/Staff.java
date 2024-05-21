package pt.ua.deti.tqs.cliniconnect.models;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.*;
import pt.ua.deti.tqs.cliniconnect.Roles;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Staff extends Persona {
    private String department;
    private String task;

    public Staff(UUID id, String name, Date dob, String email, String password, String phone, String address,
            String city, String department, String task) {
        super(id, name, dob, email, password, phone, address, city, Roles.STAFF);
        this.department = department;
        this.task = task;
    }

    @ManyToMany(mappedBy = "staffs")
    @JsonManagedReference
    private Set<Hospital> hospitals;

    @Override
    public String getUsername() {
        return super.getEmail();
    }
}