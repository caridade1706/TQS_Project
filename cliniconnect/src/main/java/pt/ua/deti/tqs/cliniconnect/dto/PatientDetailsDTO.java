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

}

