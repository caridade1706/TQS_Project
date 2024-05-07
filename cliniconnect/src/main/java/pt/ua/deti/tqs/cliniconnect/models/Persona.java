package pt.ua.deti.tqs.cliniconnect.models;

import java.util.Date;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class Persona {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
    private UUID id;

    private String name;
    private Date dob;
    private String email;
    private String password;
    private String phone;
    private String address;
    private String city;
}