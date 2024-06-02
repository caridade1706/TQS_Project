package pt.ua.deti.tqs.cliniconnect.services.impl;

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

    private SpecialtiesRepository specialtiesRepository;

    @Override
    public Specialties addSpecialty(Patient persona, HashMap<String, Integer> specialties) {
        Specialties specialtiesObject = specialtiesRepository.findByPatient(persona);

        HashMap<String, Integer> specialtiesRetrive = specialtiesObject.getSpecialty();

        if (specialtiesRetrive != null) {
            specialtiesRetrive.putAll(specialties);
            specialtiesObject.setSpecialty(specialtiesRetrive);
            return specialtiesRepository.save(specialtiesObject);
        } else {
            specialtiesObject.setSpecialty(specialtiesRetrive);
            return specialtiesRepository.save(specialtiesObject);
        }
    }

    @Override
    public HashMap<String, Integer> getByPatient(Patient persona) {

        HashMap<String, Integer> specialties = new HashMap<>();
        Specialties specialtieObject = specialtiesRepository.findByPatient(persona);
        if (specialtieObject != null) {
            specialties = specialtieObject.getSpecialty();
        }
        return specialties;
    }

    @Override
    public Specialties deleteSpecialty(Patient persona, String specialty) {
        Specialties specialtiesObject = specialtiesRepository.findByPatient(persona);

        if (specialtiesObject == null) {
            Specialties specialties = new Specialties();
            specialties.setPatient(persona);
            specialties.setSpecialty(new HashMap<>());
            return specialtiesRepository.save(specialties);
        }

        HashMap<String, Integer> specialties = specialtiesObject.getSpecialty();

        if (specialties == null || !specialties.containsKey(specialty)) {
            return specialtiesObject;
        } else {
            specialties.remove(specialty);
            specialtiesObject.setSpecialty(specialties);
            return specialtiesRepository.save(specialtiesObject);
        }
    }
}