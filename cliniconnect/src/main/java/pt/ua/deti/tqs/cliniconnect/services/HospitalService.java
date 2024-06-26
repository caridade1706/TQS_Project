package pt.ua.deti.tqs.cliniconnect.services;

import pt.ua.deti.tqs.cliniconnect.dto.CreateHospitalDTO;
import pt.ua.deti.tqs.cliniconnect.models.Hospital;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
public interface HospitalService {

    Hospital saveHospital(CreateHospitalDTO createHospitalDTO);
    Optional<Hospital> getHospitalById(UUID id);
    List<Hospital> getAllHospitals();
    void deleteHospital(UUID id);
}