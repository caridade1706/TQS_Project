package pt.ua.deti.tqs.cliniconnect.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Specialties {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "persona")
    private Patient patient;

    private HashMap<String, Integer> specialties;

    public Specialties(Patient patient, HashMap<String, Integer> specialties) {
        this.patient = patient;
        this.specialties = specialties;
    }
 
}
