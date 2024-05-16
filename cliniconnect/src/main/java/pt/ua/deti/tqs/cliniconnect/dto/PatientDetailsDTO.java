package pt.ua.deti.tqs.cliniconnect.dto;

import pt.ua.deti.tqs.cliniconnect.models.Patient;

import java.util.Map;

public class PatientDetailsDTO {
    private Patient patient;
    private Map<String, Integer> specialties;

    public PatientDetailsDTO(Patient patient, Map<String, Integer> specialties) {
        this.patient = patient;
        this.specialties = specialties;
    }

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

