package pt.ua.deti.tqs.cliniconnect.dto;

import pt.ua.deti.tqs.cliniconnect.models.Patient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatientDetailsDTO {
    private Patient patient;
    private Map<String, Integer> specialties;

    // Getters and setters
    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Map<String, Integer> getSpecialties() {
        return specialties;
    }

    public void setSpecialties(Map<String, Integer> specialties) {
        this.specialties = specialties;
    }
}

