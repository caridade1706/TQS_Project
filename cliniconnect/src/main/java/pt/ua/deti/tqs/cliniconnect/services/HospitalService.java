package pt.ua.deti.tqs.cliniconnect.services;

import pt.ua.deti.tqs.cliniconnect.models.Hospital;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public interface HospitalService {

    Hospital saveHospital(Hospital hospital);

    Optional<Hospital> getHospitalById(UUID id);

    List<Hospital> getAllHospitals();

    void deleteHospital(UUID id);
}