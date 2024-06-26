package pt.ua.deti.tqs.cliniconnect.models;

import java.time.LocalTime;
import java.util.Date;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Appointment implements java.io.Serializable  {
    
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
    private UUID id;

    private Date date;
    private LocalTime time;
    private String status;
    private double price;
    private String type;
    private String currency;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    // @JsonBackReference
    @JsonManagedReference
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    // @JsonBackReference
    @JsonManagedReference
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "hospital_id")
    // @JsonBackReference
    @JsonManagedReference
    private Hospital hospital;
}