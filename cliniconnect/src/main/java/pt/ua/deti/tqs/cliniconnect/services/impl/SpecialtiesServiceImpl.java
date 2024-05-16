package pt.ua.deti.tqs.cliniconnect.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import pt.ua.deti.tqs.cliniconnect.models.Specialties;
import pt.ua.deti.tqs.cliniconnect.repositories.SpecialtiesRepository;
import pt.ua.deti.tqs.cliniconnect.services.SpecialtiesService;
import pt.ua.deti.tqs.cliniconnect.models.Patient;

import java.util.HashMap;

@Service
@AllArgsConstructor
public class SpecialtiesServiceImpl implements SpecialtiesService {

    @Autowired
    private SpecialtiesRepository specialtiesRepository;

    @Override
    public Specialties addSpecialty(Patient persona, HashMap<String, Integer> specialties) {
        HashMap<String, Integer> existingSpecialties = specialtiesRepository.findByPatient(persona);
        if (existingSpecialties != null) {
            existingSpecialties.putAll(specialties);
            return specialtiesRepository.save(new Specialties(persona, existingSpecialties));
        } else {
            return specialtiesRepository.save(new Specialties(persona, specialties));
        }
    }

    @Override
    public HashMap<String, Integer> getByPatient(Patient persona) {
        HashMap<String, Integer> specialties = specialtiesRepository.findByPatient(persona);
        return specialties != null ? specialties : new HashMap<>();
    }

    @Override
    public Specialties deleteSpecialty(Patient persona, String specialty) {
        HashMap<String, Integer> specialties = specialtiesRepository.findByPatient(persona);
        if (specialties != null) {
            specialties.remove(specialty);
            return specialtiesRepository.save(new Specialties(persona, specialties));
        }
        return new Specialties(persona, new HashMap<>());  // Retorna um objeto vazio se não encontrar
    }
}
