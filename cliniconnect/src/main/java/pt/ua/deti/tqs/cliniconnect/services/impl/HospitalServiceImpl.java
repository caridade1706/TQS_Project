package pt.ua.deti.tqs.cliniconnect.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ua.deti.tqs.cliniconnect.models.Hospital;
import pt.ua.deti.tqs.cliniconnect.repositories.HospitalRepository;
import pt.ua.deti.tqs.cliniconnect.services.HospitalService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class HospitalServiceImpl implements HospitalService {

    @Autowired
    private HospitalRepository hospitalRepository;

    @Override
    public Hospital saveHospital(Hospital hospital) {
        return hospitalRepository.save(hospital);
    }

    @Override
    public Optional<Hospital> getHospitalById(UUID id) {
        return hospitalRepository.findById(id);
    }

    @Override
    public List<Hospital> getAllHospitals() {
        return hospitalRepository.findAll();
    }

    @Override
    public void deleteHospital(UUID id) {
        hospitalRepository.deleteById(id);
    }
}