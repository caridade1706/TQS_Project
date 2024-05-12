package pt.ua.deti.tqs.cliniconnect.models;

import jakarta.persistence.*;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Hospital {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
    private UUID id;

    private String name;
    private String address;
    private String city;

    @ManyToMany
    @JoinTable(
        name = "doctor_hospitals",
        joinColumns = @JoinColumn(name = "hospital_id"),
        inverseJoinColumns = @JoinColumn(name = "staff_id")
    )
    private Set<Staff> staffs;

    @ManyToMany
    @JoinTable(
        name = "staff_hospitals",
        joinColumns = @JoinColumn(name = "hospital_id"),
        inverseJoinColumns = @JoinColumn(name = "doctor_id")
    )
    private Set<Doctor> doctors;

    @OneToMany(mappedBy = "hospital")
    private Set<Appointment> appointments;

    @OneToOne(mappedBy = "hospital")
    private QueueManagement queueManagement;
}
