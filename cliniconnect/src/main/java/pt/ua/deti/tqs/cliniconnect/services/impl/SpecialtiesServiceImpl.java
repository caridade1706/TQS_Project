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
    public Specialties addSpecialty( Patient persona,HashMap<String, Integer> specialties) {
        if (specialtiesRepository.findByPatient(persona) != null) {
            HashMap<String, Integer>  s = specialtiesRepository.findByPatient(persona);
            s.putAll(specialties);
            return specialtiesRepository.save(new Specialties(persona, s));
        }
        else {
            return specialtiesRepository.save(new Specialties(persona, specialties));
        }
    }

    @Override
    public HashMap<String, Integer> getByPatient(Patient persona) {
        return specialtiesRepository.findByPatient(persona);
    }

    @Override
    public Specialties deleteSpecialty(Patient persona, String specialty) {
        HashMap<String, Integer> s = specialtiesRepository.findByPatient(persona);
        if (s != null) {
            s.remove(specialty);
            return specialtiesRepository.save(new Specialties(persona, s));
        }
        return null;
    }
}

